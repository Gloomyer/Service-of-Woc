package com.gloomyer.woc.utils;

import java.io.*;

/**
 * Created by Gloomy on 2016/10/26.
 */
public class PasswordUtils {
    private static String pwd;
    private static long fileSize = -1;

    public static String getPwd() {
        synchronized (PasswordUtils.class) {
            BufferedReader br = null;
            try {
                File pwdFile = new File(getPwdFile());
                if (pwdFile.length() != fileSize) {
                    fileSize = pwdFile.length();
                    br = new BufferedReader(new FileReader(pwdFile));
                    pwd = br.readLine();
                }
            } catch (Exception e) {
                pwd = null;
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                } catch (Exception e) {
                }
            }
        }
        return pwd;
    }

    private static String getPwdFile() {
        String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
        //path = path.replace('/', '\\'); // 将/换成\
        path = path.replace("file:", ""); //去掉file:
        path = path.replace("classes/", ""); //去掉class\
        path = path.substring(1); //去掉第一个\,如 \D:\JavaWeb...
        path += "password.pwd";
        return "/" + path;
    }

    /**
     * 验证密码是否正确
     *
     * @param pwd 要验证的密码
     * @return 是否正确
     */
    public static boolean isExactness(String pwd) {
        String successPwd = getPwd();
        if (successPwd != null && pwd != null) {
            return successPwd.equals(pwd);
        }
        return false;
    }
}
