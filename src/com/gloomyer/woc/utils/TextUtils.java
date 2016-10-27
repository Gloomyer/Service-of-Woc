package com.gloomyer.woc.utils;

/**
 * Created by Gloomy on 2016/10/25.
 */
public class TextUtils {

    public static boolean isEmpty(String text) {
        if (text == null || text.length() <= 0)
            return true;
        return false;
    }
}
