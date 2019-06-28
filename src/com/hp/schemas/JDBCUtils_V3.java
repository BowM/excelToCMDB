package com.hp.schemas;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

class JDBCUtils_V3 {
    private static String driver;
    private static String Remedyurl;
    private static String Remedyusername;
    private static String Remedypassword;
    private static String UCMDBurl;
    private static String UCMDBusername;
    private static String UCMDBpassword;

    static {
        try {
            // 1.通过当前类获取类加载
            ClassLoader classLoader = JDBCUtils_V3.class.getClassLoader();
            // 2.通过类加载器的方法获得一个输入流
            InputStream is = classLoader.getResourceAsStream("db.properties");
            // 3.创建个properties对象
            Properties props = new Properties();
            // 4.加载输入
            props.load(is);
            // 5.获取相关参数的
            driver = props.getProperty("driver");
            Remedyurl = props.getProperty("Remedyurl");
            Remedyusername = props.getProperty("Remedyusername");
            Remedypassword = props.getProperty("Remedypassword");
            UCMDBurl = props.getProperty("UCMDBurl");
            UCMDBusername = props.getProperty("UCMDBusername");
            UCMDBpassword = props.getProperty("UCMDBpassword");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接方法
     */
    static Connection getConnection(String type) {
        try {
            Connection conn;
            Class.forName(driver);
            if(type.equals("remedy")){
                conn = DriverManager.getConnection(Remedyurl, Remedyusername, Remedypassword);
                return conn;
            }else if(type.equals("UCMDB")){
                conn = DriverManager.getConnection(UCMDBurl, UCMDBusername, UCMDBpassword);
                return conn;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 释放连接方法
     */
    static void release(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
