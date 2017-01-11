package com.gloomyer.woc.utils;


import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Gloomy on 2016/10/27.
 */
public class JDBCUtils {
    public static final ComboPooledDataSource dataSource;

    static {
        dataSource = new ComboPooledDataSource();
        try {
//            dataSource.setDriverClass("com.mysql.jdbc.Driver");
//            dataSource.setJdbcUrl("jdbc:mysql:///woc");
//            dataSource.setUser("root");
//            dataSource.setPassword("123456");
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
            return dataSource.getConnection();
        } catch (SQLException e) {
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
