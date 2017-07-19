package com.pxene.pap.repository.custom;

import com.pxene.pap.domain.beans.CampaignBean;
import com.pxene.pap.domain.models.AppTargetDetailModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wangshuai on 2017/7/19.
 */
public interface CustomAppDao {

    public int selectAppNum(@Param("adx")String adx, @Param("includes")CampaignBean.Target.Include[] includes, @Param("encludes")CampaignBean.Target.Exclude[] excludes);

    public List<String> selectAppIds(@Param("adx")String adx, @Param("includes")List<AppTargetDetailModel> includes, @Param("encludes")List<AppTargetDetailModel> excludes);

}
