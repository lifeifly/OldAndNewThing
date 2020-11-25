package com.example.oldandnewthing.model.base;

public abstract class MvpBaseModel {
    //请求广告数据
    public abstract void requestBannerData();
    //请求列表数据
    public abstract void requestListData();
    //请求文章内容数据
    public abstract void requestArticle(String uri);
    //请求图片内容数据
    public abstract void requestPhoto(String uri);
    //请求视频数据
    public abstract  void requestVideo(String uri);
}
