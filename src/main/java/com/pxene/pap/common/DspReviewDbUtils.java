package com.pxene.pap.common;

import com.pxene.pap.constant.ConfKeyConstant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.sql.*;

/**
 * dsp hadoop库操作
 * Created by wangshuai on 2017/7/11.
 */
public class DspReviewDbUtils {

    private static Logger log = Logger.getLogger(DspReviewDbUtils.class);

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(Environment env) {

        String driver = env.getProperty(ConfKeyConstant.DSP_REVIEW_DB_DRIVER);
        String url = env.getProperty(ConfKeyConstant.DSP_REVIEW_DB_URL);
        String user = env.getProperty(ConfKeyConstant.DSP_REVIEW_DB_USER);
        String password = env.getProperty(ConfKeyConstant.DSP_REVIEW_DB_PWD);

        Connection conn = null;
        try {
            Class.forName(driver);

        } catch (ClassNotFoundException e) {
            log.error("dsp mysql get driver failure ",e);
            return null;
        }
        try {
            conn = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            log.info("dspmysql getConnection exception ",e);
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     * @author wangshuai 2017-02-15
     * @param conn
     * @param ps
     *
     */
    public static void close(Connection conn,PreparedStatement ps){

        try {
            if(ps!=null){
                ps.close();
            }
            if(conn!=null){
                conn.close();
            }

        } catch (SQLException e) {
            log.info("pap数据库关闭异常");
            e.printStackTrace();
        }

    }

    /**
     * 关闭数据库连接
     * @author wangshuai 2017-02-15
     * @param conn
     * @param ps
     * @param rs
     */
    public static void close(Connection conn,PreparedStatement ps,ResultSet rs){

        try {
            if(rs!=null){
                rs.close();
            }

            if(ps!=null){
                ps.close();
            }

            if(conn!=null){
                conn.close();
            }
        } catch (SQLException e) {
            log.info("pap数据库关闭异常");
            e.printStackTrace();
        }

    }
}
