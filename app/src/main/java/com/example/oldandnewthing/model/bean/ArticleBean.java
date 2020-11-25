package com.example.oldandnewthing.model.bean;

import java.util.List;
import java.util.Map;

//文章实体类
public class ArticleBean {
    private String title;
    private String keyword;
    private String source;
    private String time;
    private String des;
    private String videoUrl;
    private String editor;
    private List<Map<String, String>> data;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public ArticleBean(String title, String des, String source, String time, String videoUrl, String editor, List<Map<String, String>> data, String keyword) {
        this.keyword=keyword;
        this.des = des;
        this.title = title;
        this.source = source;
        this.time = time;
        this.videoUrl = videoUrl;
        this.editor = editor;
        this.data = data;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public List<Map<String, String>> getData() {
        return data;
    }

    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }
}
