package com.example.oldandnewthing.model.bean;

public class VideoBean {
    private String header;
    private String keyword;
    private String editor;
    private String breif;
    private String centerId;

    public VideoBean(String header, String keyword, String editor, String breif, String centerId) {
        this.header = header;
        this.keyword = keyword;
        this.editor = editor;
        this.breif = breif;

        this.centerId = centerId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getBreif() {
        return breif;
    }

    public void setBreif(String breif) {
        this.breif = breif;
    }


    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }
}
