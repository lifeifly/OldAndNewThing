package com.example.oldandnewthing.presenter.base;

public abstract class MvpBasePresenter<T> {
    //绑定视图
    public T view;

    public MvpBasePresenter(T view) {
        this.view = view;
    }

    //向Model请求广告数据
    public abstract void requestBannerDataToModel();

    //向Model请求列表数据
    public abstract void requestListDataToModel();

    //请求文章数据
    public abstract void requestArticleData(String uri);
    //请求图片数据
    public abstract void requestPhotoData(String uri);
    //请求视频数据
    public abstract void requestVideoData(String uri);
    //请求搜索数据
    public abstract void requestSearchData(String keyword);
}
