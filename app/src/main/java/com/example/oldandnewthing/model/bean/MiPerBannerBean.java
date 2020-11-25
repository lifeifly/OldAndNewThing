package com.example.oldandnewthing.model.bean;

import java.io.Serializable;

//人物、军事、快看的分类的广告3个参数的实体类
public class MiPerBannerBean implements Serializable {
    private String href;
    private String des;
    private String src;

    public MiPerBannerBean(String href, String des, String src) {
        this.href = href;
        this.des = des;
        this.src = src;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
