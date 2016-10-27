package com.gloomyer.woc.model;

import java.util.List;

/**
 * Created by Gloomy on 2016/10/25.
 */
public class ResultModel {

    private int statusCode;
    private boolean success;
    private String message;
    private List<? extends Object> result;

    public ResultModel() {
    }

    public ResultModel(int statusCode, boolean success, String message) {
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
    }

    public ResultModel(int statusCode, boolean success, String message, List<? extends Object> result) {
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
        this.result = result;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<? extends Object> getResult() {
        return result;
    }

    public void setResult(List<? extends Object> result) {
        this.result = result;
    }
}
