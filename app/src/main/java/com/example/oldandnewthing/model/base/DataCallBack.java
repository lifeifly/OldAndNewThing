package com.example.oldandnewthing.model.base;

import com.example.oldandnewthing.model.bean.ArticleBean;
import com.example.oldandnewthing.model.bean.PhotoBean;
import com.example.oldandnewthing.model.bean.VideoBean;

import java.util.List;
import java.util.Map;

public interface DataCallBack {
    //广告数据请求成功，回调
    void onBannerSuccess(Object data);

    //广告数据请求失败
    void onBannerError(String result);

    //列表数据请求成功
    void onListSuccess(Object data);

    //列表数据请求失败
    void onListError(String error);

    //文章数据请求成功
    void onAricleSuccess(ArticleBean articleBean);

    //文章数据请求失败
    void onArticleError(String error);

    //图片数据请求成功
    void onPhotoSuccess(PhotoBean photoBean);

    //图片数据请求失败
    void onPhotoError(String error);

    //视频请求成功
    void onVideoSuccess(VideoBean videoBean);

    //视频请求失败
    void onVideoError(String error);

    //请求搜索数据成功
    void searchSuccess(List<Map<String,Object>> data);

    //请求搜索数据失败
    void searchError();
}
