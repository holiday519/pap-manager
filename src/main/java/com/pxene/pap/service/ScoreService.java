package com.pxene.pap.service;

import static com.pxene.pap.constant.RedisKeyConstant.CREATIVE_DATA_DAY;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.script.ScriptException;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.RedisHelper;
import com.pxene.pap.common.ScriptUtils;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.domain.beans.CampaignBean;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
import com.pxene.pap.domain.models.EffectModel;
import com.pxene.pap.domain.models.EffectModelExample;
import com.pxene.pap.domain.models.FormulaModel;
import com.pxene.pap.domain.models.FormulaModelExample;
import com.pxene.pap.domain.models.LandpageCodeHistoryModel;
import com.pxene.pap.domain.models.LandpageCodeHistoryModelExample;
import com.pxene.pap.domain.models.RuleModel;
import com.pxene.pap.domain.models.RuleModelExample;
import com.pxene.pap.domain.models.StaticModel;
import com.pxene.pap.exception.ServerFailureException;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.EffectDao;
import com.pxene.pap.repository.basic.FormulaDao;
import com.pxene.pap.repository.basic.LandpageCodeHistoryDao;
import com.pxene.pap.repository.basic.RuleDao;
import com.pxene.pap.repository.basic.StaticDao;

/**
 * 评分服务
 * @author ningyu
 */
@Service
public class ScoreService
{
    private static final SimpleDateFormat DATATIME_FORMATTER = new SimpleDateFormat("yyyyMMdd");

    private static final String REGEX_UUID36_TEMPLATE = "'{'{0}'}'";

    private static final String REGEX_UUID36_VALUE = "\\{(.{36})\\}";

    private static final Logger LOGGER = LoggerFactory.getLogger(ScoreService.class);
    
    @Autowired
    private StaticDao staticDao;
    
    @Autowired
    private RuleDao ruleDao;
    
    @Autowired
    private FormulaDao formulaDao;
    
    @Autowired
    private EffectDao effectDao;
    
    @Autowired
    private CreativeDao creativeDao;
    
    @Autowired
    private LandpageCodeHistoryDao landpageCodeHistoryDao;
    
    @Autowired
    private RedisHelper tertiaryRedis;
    
    @Autowired
    private DataService dataService;
    
    
    @PostConstruct
    public void selectRedis()
    {
        tertiaryRedis.select("redis.tertiary.");
    }
    
    
    /**
     * 计算活动得分。
     * @param projectId     项目ID
     * @param campaignId    活动ID
     * @param beginTime     活动起始日期
     * @param endTime       活动结束日期
     * @return  活动得分
     */
    public double getCampaignScore(String projectId, String campaignId, Long beginTime, Long endTime)
    {
        // 声明活动总得分
        double result = 0.0;
        
        try
        {
            // 查询效果数据（A1~A10）
            Map<String, Number> effectSum = getEffectSum(projectId, campaignId, new Date(beginTime), new Date(endTime));
            
            // 查询实时统计数据（B1~B5）
            Map<String, Number> realTimeSum = getRealTimeSum(campaignId, beginTime, endTime);
            
            // 选出满足触发条件的规则
            RuleModel rule = pickUpRule(projectId, campaignId, endTime, endTime, effectSum, realTimeSum);
            
            if (rule == null)
            {
                return 0.0;
            }
            else
            {
                // 查询出属于这个触发条件的全部公式
                FormulaModelExample example = new FormulaModelExample();
                example.createCriteria().andRuleIdEqualTo(rule.getId());
                List<FormulaModel> formulaModels = formulaDao.selectByExample(example);
                
                for (FormulaModel formulaModel : formulaModels)
                {
                    String formula = formulaModel.getFormula();
                    Double baseVal = getStaticValueById(formulaModel.getStaticId());
                    Double forwardVernier = formulaModel.getForwardVernier();
                    Double negativeVernier = formulaModel.getNegativeVernier();
                    Float weight = formulaModel.getWeight();
                    
                    // 替换公式中的静态值
                    formula = replaceFormulaStaticValue(formula);
                    
                    // 替换公式中的变量值
                    formula = replaceFormulaVariableValue(formula, effectSum);
                    formula = replaceFormulaVariableValue(formula, realTimeSum);
                    
                    // 计算公式的结果
                    double formulaResult = computeFormula(formula);
                    
                    // 根据游标系计算得分
                    double score = score(formulaResult, baseVal, forwardVernier, negativeVernier);
                    
                    // 一个评分规则有多条公式，每个公式计算出的得分需要乘权重，才是这个公式的最终得分
                    double weightedScore = score * weight;
                    
                    result = result + weightedScore;
                }
            }
        }
        catch (ScriptException e)
        {
            throw new ServerFailureException(PhrasesConstant.RULE_TRIGGER_ERROR);
        }
        
        return result;
    }


    /**
     * 查询在指定的日期段内，某活动的全部效果数据（从客户回收的数据）的总和（A1~A10）。
     * @param campaignId    活动ID
     * @param startDate     起始日期
     * @param endDate       结束日期
     * @return  保存着效果数据变量值的Map，其中Map的Key表示变量名，即“A1”~“A10”，Value表示变量值
     */
    private Map<String, Number> getEffectSum(String projectId, String campaignId, Date startDate, Date endDate)
    {
        Map<String, Number> result = new HashMap<String, Number>();
        for (int i = 1; i <= 10; i++)
        {
            result.put("A" + i, 0.0D);
        }
        
        EffectModelExample effectExample = new EffectModelExample();
        
        // 根据活动ID和日期段从“pap_t_landpage_code_history”表中查询监测码的使用历史
        LandpageCodeHistoryModelExample historyCodeExample = new LandpageCodeHistoryModelExample();
        historyCodeExample.createCriteria().andCampaignIdEqualTo(campaignId).andStartTimeGreaterThanOrEqualTo(startDate).andEndTimeLessThanOrEqualTo(endDate);
        List<LandpageCodeHistoryModel> items = landpageCodeHistoryDao.selectByExample(historyCodeExample);
        
        for (LandpageCodeHistoryModel item : items)
        {
            String codesStr = item.getCodes();
            Date startTime = item.getStartTime();
            Date endTime = item.getEndTime();
            List<Date> days = DateUtils.listDatesBetweenTwoDates(new LocalDate(startTime), new LocalDate(endTime), true);
            
            if (!StringUtils.isEmpty(codesStr))
            {
                String[] codeArray = codesStr.split(",");
                
                // 找到“pap_t_effect”表中，字段date在给定时间段内且字段code在给定的监码表列中的全部记录
                effectExample.clear();
                effectExample.createCriteria().andProjectIdEqualTo(projectId).andDateIn(days).andCodeIn(Arrays.asList(codeArray));
                List<EffectModel> effects = effectDao.selectByExample(effectExample);
                
                // 累加求和
                for (EffectModel effect : effects)
                {
                    Double[] valArray = buildEffectValArray(effect);
                    
                    doAccumulate(result, valArray);
                }
            }
        }
        
        return result;
    }


    /**
     * 查询在指定的日期段内，某活动的全部实时统时数据的总和（B1~B5）
     * @param campaignId    活动ID
     * @param startDate     起始日期
     * @param endDate       结束日期
     * @return 保存着实时统计数据变量值的Map，其中Map的Key表示变量名，即“B1”~“B5”，Value表示变量值
     */
    private Map<String, Number> getRealTimeSum(String campaignId, Long startDateTimestamp, Long endDateTimestamp)
    {
        Map<String, Number> result = new HashMap<String, Number>();
        for (int i = 1; i <= 5; i++)
        {
            result.put("B" + i, 0);
        }
        
        LocalDate startDate = new LocalDate(startDateTimestamp);
        LocalDate endDate = new LocalDate(endDateTimestamp);
        
        // 获得开始日期和结束日期之间的全部日期
        List<Date> days = DateUtils.listDatesBetweenTwoDates(startDate, endDate, true);
        
        if (days == null || days.isEmpty())
        {
            throw new IllegalArgumentException();
        }
        
        int pvAmount = 0;
        int clickAmount = 0;
        int secondJumpAmount = 0;
        double expenseAmount = 0;
        
        // 根据活动ID查出数据库中该活动的全部创意
        CreativeModelExample example = new CreativeModelExample();
        example.createCriteria().andCampaignIdEqualTo(campaignId);
        List<CreativeModel> creatives = creativeDao.selectByExample(example);

        if (creatives != null && creatives.size() > 0)
        {
            for (CreativeModel creative : creatives)
            {
                // 拼接“creativeDataDay_创意ID”作为Redis的Key
                String key = CREATIVE_DATA_DAY + creative.getId();
                
                // 遍历活动开始日期和结束日期之间的每一天，作为Hash的Field
                for (Date day : days)
                {
                    String datePrefix = DATATIME_FORMATTER.format(day);
                    
                    String pvField = datePrefix + "@m";
                    String cField = datePrefix + "@c";
                    String secondJumpField = datePrefix + "@j";
                    String expenseField = datePrefix + "@e";
                    
                    String pvStr = tertiaryRedis.hget(key, pvField);
                    String clickStr = tertiaryRedis.hget(key, cField);
                    String secondJumpStr = tertiaryRedis.hget(key, secondJumpField);
                    String expenseStr = tertiaryRedis.hget(key, expenseField);
                    
                    if (!StringUtils.isEmpty(pvStr))
                    {
                        pvAmount = pvAmount + Integer.valueOf(pvStr);
                    }
                    if (!StringUtils.isEmpty(clickStr))
                    {
                        clickAmount = clickAmount + Integer.valueOf(clickStr);
                    }
                    if (!StringUtils.isEmpty(secondJumpStr))
                    {
                        secondJumpAmount = secondJumpAmount + Integer.valueOf(secondJumpStr);
                    }
                    if (!StringUtils.isEmpty(expenseStr))
                    {
                        expenseAmount = expenseAmount + Float.valueOf(expenseStr);
                    }
                }
            }
            
            // 获得修正成本
            CampaignBean campaignData = (CampaignBean) dataService.getCampaignData(campaignId, startDateTimestamp, endDateTimestamp);
            Double adxCost = campaignData.getAdxCost();
            
            result.put("B1", pvAmount);
            result.put("B2", clickAmount);
            result.put("B3", secondJumpAmount);
            result.put("B4", expenseAmount);
            result.put("B5", adxCost);
        }
        
        return result;
    }
    
    
    /**
     * 遍历项目的全部评分规则，逐个判断规则的触条发件，选出第一个触发条件满足的评分规则。
     * @param projectId     项目ID
     * @param campaignId    活动ID
     * @param beginTime     活动起始日期
     * @param endTime       活动截止日期
     * @param effectSum     保存着效果回收数据变量值的Map
     * @param realTimeSum   保存着实时统计数据变量值的Map
     * @return  第一条触发条件被满足的评分规则（按更新时间降序）
     * @throws ScriptException
     */
    private RuleModel pickUpRule(String projectId, String campaignId, Long beginTime, Long endTime, Map<String, Number> effectSum, Map<String, Number> realTimeSum) throws ScriptException
    {
        // 根据项目ID查询这个项目的全部评分规则，排序顺序为按更新时间降序
        RuleModelExample example = new RuleModelExample();
        example.createCriteria().andProjectIdEqualTo(projectId);
        example.setOrderByClause("update_time DESC");
        List<RuleModel> rules = ruleDao.selectByExample(example);
        
        for (RuleModel rule : rules)
        {
            String condition = rule.getConditions();
            String relation = rule.getRelation();
            String staticId = rule.getStaticId();
            
            if (org.apache.commons.lang3.StringUtils.isAnyEmpty(condition, relation, staticId))
            {
                throw new IllegalArgumentException();
            }
            
            // 拼接触发条件
            String trigger = condition + relation + "{" + staticId + "}";
            
            // 替换触发条件中的静态值
            trigger = replaceFormulaStaticValue(trigger);
    
            // 替换触发条件中的变量值
            trigger = replaceFormulaVariableValue(trigger, effectSum);
            trigger = replaceFormulaVariableValue(trigger, realTimeSum);
            
            // 判断触发条件是否成立
            boolean isSuccessTrigger = judgeTrigger(trigger);
            
            if (isSuccessTrigger)
            {
                return rule;
            } 
        }
        
        return null;
    }


    /**
     * 累加求和：将传入的包含A1~A10的数组，分别累加到Map中对应的A1~A10中。即，valArray[0]累加到Map Key[A1]，valArray[9]累加到Map Key[A10]。
     * @param sumMap    保存累加和的Map
     * @param valArray  从效果数据提取出来的A1~A10的数组
     */
    private void doAccumulate(Map<String, Number> sumMap, Double[] valArray)
    {
        for (int i = 1; i <= 10; i++)
        {
            String key = "A" + i;
            
            Number oldVal = sumMap.get(key);
            Number newVal = oldVal.doubleValue() + valArray[i-1];
            sumMap.put(key, newVal);
        }
    }

    /**
     * 从EffectModel对象中取出A1~A10这10个属性，放到一个Double类型的数组中。
     * @param effect    效果数据对象
     * @return  Double类型数组，索引0~9，分别保存A1~A10这10个属性
     */
    private Double[] buildEffectValArray(EffectModel effect)
    {
        Double[] valArray = new Double[10];
        
        Class<EffectModel> effectModelClass = EffectModel.class;
        for (int i = 1; i <= 10; i++)
        {
            Method method;
            try
            {
                method = effectModelClass.getDeclaredMethod("getA" + i);
                valArray[i-1] = (Double) method.invoke(effect);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return valArray;
    }
    
    /**
     * 使用脚本引擎计算公式结果是多少。
     * @param formula 需要计算的公式，如：(A1+A2)*c+B3+{07346e77-1be5-49ad-9e4d-4e1541513926}+{5e2ea473-2a22-4ea4-aa57-52feedd7d2a2}
     * @return  公式的计算结果
     * @throws ScriptException
     */
    private double computeFormula(String formula) throws ScriptException
    {
        double score = ScriptUtils.compute(formula);
        
        return score;
    }
    
    /**
     * 使用脚本引擎判断触发条件是否达到。
     * @param trigger 需要验证的触发条件，如：(A1+A2)*c+B3+{07346e77-1be5-49ad-9e4d-4e1541513926}+{5e2ea473-2a22-4ea4-aa57-52feedd7d2a2}>1000
     * @return 是否被成功触发，true：是，false：否
     * @throws ScriptException
     */
    private boolean judgeTrigger(String trigger) throws ScriptException
    {
        boolean flag = ScriptUtils.judge(trigger);
        
        return flag;
    }
    

    /**
     * 为公式的结果进行打分。
     * 
     * <pre>
     * 评分规则根据如下坐标轴：
     * base+bs         baseVal           base+fs
     * o-----------------o------------------o
     * 0%               50%                 100%
     * </pre>
     * 
     * 说明：
     *  <pre>
     * <----------------------BEGIN---------------------->
     * 假设公式性质为计算成本，成本越低评分越高，反之成本越高评分越低。
     * 
     * 参考值：60.0
     * 正向游标：-50.0
     * 反向游标：70.0
     * 130.0(零分) ----- 60.0 ----- 10.0(满分)
     * 
     * 在正向区间(60.0  ~ 10.0)内，1元钱可以打多少分：50分数 ÷ 50元钱 = 1分数/元
     * 在负向区间(130.0 ~ 60.0)内，1元钱可以打多少分：50分数 ÷ 70元钱 = 0.714分数/元
     * 
     * 公式结果：75元，75元 - 60元  = 15元，15元 × 0.714分数/元 = 15分，再加上基础分数50，最终打分：60.714
     * 公式结果：45元，45元 - 60元= -15元， -15元 × 1分数/元 = -15分，再加上基础分数50，最终打分：35
     * <----------------------END---------------------->
     * 
     * 
     * 
     * <----------------------BEGIN---------------------->
     * 假设公式性质为计算核卡数量，数量越高评分越高，反之数量越低评分越低。
     * 
     * 参考值：60.0
     * 正向游标：50.0
     * 反向游标：-30.0
     * 30.0(零分) ----- 60.0 ----- 110.0(满分)
     * 
     * 在正向区间(60.0 ~ 110.0)内，一次核卡可以打多少分：50分数 ÷ 50次 = 1分数/次
     * 在负向区间(30.0 ~  60.0)内，一次核卡可以打多少分：50分数 ÷ 30次 = 1.666分数/次
     * 
     * 公式结果：75次，75次 - 60次 = 15次, 15次 × 1分数/次 = 15分，再加上基础分数50，最终打分：65
     * 公式结果：45次，45次 - 60次 = -15次，-15次 × 1.666分数/次 = -15次，再加上基础分数50，最终打分：25
     * <----------------------END---------------------->
     * </pre>
     * 
     * @param target        公式的计算结果，即待评分的值，这个值可能代表“成本”、“核卡数”、“注册数”等。
     * @param baseVal       参考值
     * @param forwardStep   正向游标
     * @param backwardStep  负向游标
     * @return 公式的得分
     */
    private double score(double target, double baseVal, double forwardStep, double backwardStep)
    {
        double scores = 0.0d;
        
        double forwardBoundary = baseVal + forwardStep;      // 正向边界
        double backwardBoundary = baseVal + backwardStep;    // 反向边界
        
        LOGGER.debug(backwardBoundary + "(零分) ----- " + baseVal + "(伍拾分) ----- " + forwardBoundary + "(满分)");
        
        if (target == backwardBoundary)
        {
            LOGGER.debug("公式结果等于反向边界值，不需计算，得分为零分");
            return 0;
        }
        
        if (target == baseVal)
        {
            LOGGER.debug("公式结果等于参考值，不需计算，得分为伍拾分");
            return 50;
        }
        
        if (target == forwardBoundary)
        {
            LOGGER.debug("公式结果等于正向边界值，不需计算，得分为满分");
            return 100;
        }
        
        double forwardSeg = Math.abs(forwardStep);
        double backwardSeg = Math.abs(backwardStep);
        
        // 目标值与参考值的差，代表有多少个评分单元。
        double units = target - baseVal;
        
        LOGGER.debug("反向区间：" + backwardSeg + ", 正向区间：" + forwardSeg);
        
        double forwardScoreUnit = 50 / forwardSeg;   // 在“正向区间”，每个评分单元可以打多少分数
        double backwardScoreUnit = 50 / backwardSeg; // 在“反向区间”，每个评分单元可以打多少分数
        
        
        if ((baseVal > backwardBoundary) && (baseVal < forwardBoundary))        // 适用于升序情况，如：30 --> 60 --> 110
        {
            if (target > baseVal)
            {
                scores = forwardScoreUnit * units;
            }
            else
            {
                scores = backwardScoreUnit * units;
            }
        }
        else if ((baseVal < backwardBoundary) && (baseVal > forwardBoundary))   // 适用于降序情况，如：130 --> 60 --> 10
        {
            if (target > baseVal)
            {
                scores = backwardScoreUnit * units;
            }
            else
            {
                scores = forwardScoreUnit * units;
            }
        }
        else
        {
            throw new IllegalArgumentException();
        }
        
        return 50 + scores;
    }
    
    /**
     * 提取公式中的变量值（如A1,B1），根据变量值代表的UUID，从效果数据表（pap_t_effect）中查询出对应的真实值，最后将变量值替换回原公式中。
     * @param formula   待操作的公式
     * @param sumMap 保存着变量值的Map，其中Map的Key表示变量名，如“A1”，Value表示变量值
     * @return  替换了变量值之后的公式
     */
    private String replaceFormulaVariableValue(String formula, Map<String, Number> sumMap)
    {
        for (Map.Entry<String, Number> entry : sumMap.entrySet())
        {
            String key = entry.getKey();
            Number val = entry.getValue();
            
            formula = StringUtils.replace(formula, key, val.toString());
        }
        
        return formula;
    }
    
    /**
     * 提取公式中的以“{”开头、“}”结尾的静态值ID，根据这个UUID，从静态值表（pap_t_static）中查询出对应的真实值，最后将静态值替换回原公式中。
     * @param formula   待操作的公式
     * @return  替换全部静态值之后的公式
     */
    private String replaceFormulaStaticValue(String formula)
    {
        Pattern pattern = Pattern.compile(REGEX_UUID36_VALUE);
        Matcher matcher = pattern.matcher(formula);
        
        while (matcher.find())
        {
            String uuid = matcher.group(1);
            String searchString = MessageFormat.format(REGEX_UUID36_TEMPLATE, uuid);
            Double staticVal = getStaticValueById(uuid);
            formula = StringUtils.replace(formula, searchString, String.valueOf(staticVal));
        }
        
        return formula;
    }
    
    /**
     * 根据静态值ID从静态值表（pap_t_static）中读取静态值。
     * @param id    静态值ID
     * @return  静态值
     */
    private Double getStaticValueById(String id)
    {
        StaticModel model = staticDao.selectByPrimaryKey(id);
        return model.getValue();
    }
}
