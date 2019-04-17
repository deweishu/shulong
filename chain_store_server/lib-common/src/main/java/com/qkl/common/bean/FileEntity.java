package com.qkl.common.bean;

import java.io.Serializable;

/**
 * @author dengjihai
 * @create 2018-03-21
 **/
public class FileEntity implements Serializable {

    private String fileName;

    private String filePath;

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
