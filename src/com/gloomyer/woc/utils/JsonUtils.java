package com.gloomyer.woc.utils;

import com.gloomyer.woc.model.ResultModel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Json格式化工具类
 */
public class JsonUtils {


    public static String toJson(int statusCode, boolean success, String message, List<? extends Object> result) {
        ResultModel model = new ResultModel(statusCode, success, message, result);
        return GsonUtils.get().toJson(model);
    }

    public static String toJson(int statusCode, boolean success, String message) {
        return toJson(statusCode, success, message, null);
    }

    public static void wrtiteJson(HttpServletResponse response, int statusCode, boolean success, String message, List<? extends Object> result) throws IOException {
        response.getWriter().write(JsonUtils.toJson(statusCode, success, message, result));
    }

    public static void wrtiteJson(HttpServletResponse response, int statusCode, boolean success, String message) throws IOException {
        JsonUtils.<String>wrtiteJson(response, statusCode, success, message, null);
    }
}
