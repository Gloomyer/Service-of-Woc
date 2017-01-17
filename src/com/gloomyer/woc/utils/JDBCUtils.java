package com.gloomyer.woc.utils;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

/**
 * Created by Gloomy on 2016/10/27.
 */
public class JDBCUtils {
    public static final ComboPooledDataSource dataSource;

    static {
        dataSource = new ComboPooledDataSource();
        try {
            SAXReader reader = new SAXReader();
            String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
            path = path.replace("file:", "");
            path = path.replace("classes/", "");
            path = path.substring(1);
            path += "mysql-connection-pool-config.xml";

            /**
             * xml Demo:
             * classmame:com.mysql.jdbc.Driver
             * url:jdbc:mysql:///woc
             * username:root
             * password:123456
             */
            Document document = reader.read(new File("/" + path));
            Element node = document.getRootElement();
            Iterator<Element> iterator = node.elementIterator();
            while (iterator.hasNext()) {
                Element e = iterator.next();
                switch (e.getName()) {
                    case "classmame":
                        dataSource.setDriverClass(e.getText());
                        break;
                    case "url":
                        dataSource.setJdbcUrl(e.getText());
                        break;
                    case "username":
                        dataSource.setUser(e.getText());
                        break;
                    case "password":
                        dataSource.setPassword(e.getText());
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接池
     *
     * @return
     */
    public static DataSource getDataSorce() {
        return dataSource;
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
