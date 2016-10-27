package com.gloomyer.woc.utils;

import com.google.gson.Gson;

/**
 * Created by Gloomy on 2016/10/25.
 */
public class GsonUtils {
    private GsonUtils() {

    }

    private static Gson mGson;

    public static Gson get() {
        synchronized (GsonUtils.class) {
            if (mGson == null) {
                mGson = new Gson();
            }
        }

        return mGson;
    }
}
