package com.example.demo.filemanager.entity;

public class FileInfo {
    private String fileName;
    private String fileUrl;

    // Constructor, getters, and setters
    public FileInfo(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }
}
