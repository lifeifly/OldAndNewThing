package com.example.oldandnewthing.model.bean;

import java.io.Serializable;

//除了人物、军事、快看的分类的广告的实体类
public class BannerBean implements Serializable {
    private String href;
    private String srcHref;
    private String title;
    private String text;

    public BannerBean(String href, String srcHref, String title, String text) {
        this.href = href;
        this.srcHref = srcHref;
        this.title = title;
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getSrcHref() {
        return srcHref;
    }

    public void setSrcHref(String srcHref) {
        this.srcHref = srcHref;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
