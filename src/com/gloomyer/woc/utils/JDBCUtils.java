package com.gloomyer.woc.utils;


import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Gloomy on 2016/10/27.
 */
public class JDBCUtils {
    public static final String url = "jdbc:mysql://localhost/woc";
    public static final String name = "com.mysql.jdbc.Driver";
    public static String user;
    public static String password;

    static {
        BufferedReader bis = null;
        try {
            Class.forName(name);
            String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
            //path = path.replace('/', '\\'); // 将/换成\
            path = path.replace("file:", ""); //去掉file:
            path = path.replace("classes/", ""); //去掉class\
            path = path.substring(1); //去掉第一个\,如 \D:\JavaWeb...
            path += "jdbc.txt";
            path = "/" + path;

            bis = new BufferedReader(new FileReader(new File(path)));
            String line = bis.readLine();
            String[] split = line.split("&");
            user = split[0];
            password = split[1];
            System.out.println("user:" + user + ",pwd:" + password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取连接
     *
     * @return
     */
    public static Connection getConn() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection conn, Statement st, ResultSet rs) {
        try {
            conn.close();
        } catch (Exception e) {
        }

        try {
            st.close();
        } catch (Exception e) {
        }

        try {
            rs.close();
        } catch (Exception e) {
        }
    }
}
