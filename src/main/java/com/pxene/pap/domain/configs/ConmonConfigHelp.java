package com.pxene.pap.domain.configs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 读取配置文件帮助类
 * Created by wangshuai on 2017/5/16.
 */
public class ConmonConfigHelp {

    private static Map<String,String> props_map= new HashMap<>();
    static {
        try {
            Properties props = new Properties();
            //读取配置文件，profiles的值
            props.load(ConmonConfigHelp.class.getResourceAsStream("/application.properties"));
            String active =  props.getProperty("spring.profiles.active");
            String configFile= "/application-"+active+".properties";
            props.clear();
            //加载对应的profiles
            props.load(ConmonConfigHelp.class.getResourceAsStream(configFile));
            //excel保存路径加入到map中
            props_map.put("EXCEL_SAVEPATH",props.getProperty("pap.excel.savePath"));
            props_map.put("EXCEL_DOWNLOAD_MAPURL",props.getProperty("pap.excel.download.mapUrl"));
            props.clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //excel保存路径
    public static final String EXCEL_SAVEPATH = props_map.get("EXCEL_SAVEPATH");
    public static final String EXCEL_DOWNLOAD_MAPURL = props_map.get("EXCEL_DOWNLOAD_MAPURL");



}
