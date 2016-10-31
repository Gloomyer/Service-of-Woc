package com.gloomyer.woc.model;

/**
 * Created by Gloomy on 2016/10/28.
 */
public class FileModel {
    private String uuid;
    private String fileName;
    private String filePath;

    public FileModel(String uuid, String fileName, String filePath) {
        this.uuid = uuid;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
