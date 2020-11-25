package com.example.oldandnewthing.model.bean;

import java.io.Serializable;

//标签分类
public class CategoryBean implements Serializable {
    private String href;
    private String title;


    public CategoryBean(String href, String title) {
        this.href = href;
        this.title = title;

    }




    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
