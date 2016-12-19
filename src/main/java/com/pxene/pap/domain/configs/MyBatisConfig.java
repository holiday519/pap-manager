package com.pxene.pap.domain.configs;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.github.pagehelper.PageHelper;

@Configuration
@MapperScan("com.pxene.pap.repository") 
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer
{
    @Autowired
    DataSource dataSource;
    
    
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean()
    {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.pxene.pap.doamin.model");
        
        // 分页插件（http://git.oschina.net/free/Mybatis_PageHelper/blob/master/wikis/HowToUse.markdown）
        PageHelper pageHelper = new PageHelper();
        Properties props = new Properties();
//        props.setProperty("dialect", "mysql");
        props.setProperty("reasonable", "false");   // 分页参数合理化，默认false禁用。启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页；禁用合理化时，如果pageNum<1或pageNum>pages会返回空数据。
        props.setProperty("pageSizeZero", "false");  // 设置为true时，如果pageSize=0或者RowBounds.limit = 0就会查询出全部的结果
        props.setProperty("supportMethodsArguments", "false");  // 支持通过Mapper接口参数来传递分页参数
        props.setProperty("returnPageInfo", "always");           // always总是返回PageInfo类型,check检查返回类型是否为PageInfo,none返回Page
        pageHelper.setProperties(props);
        bean.setPlugins(new Interceptor[]{ pageHelper });
        
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try
        {
            bean.setMapperLocations(resolver.getResources("classpath:com/pxene/pap/repository/**/*.xml"));
            return bean.getObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory)
    {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    
    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager()
    {
        return new DataSourceTransactionManager(dataSource);
    }
}
