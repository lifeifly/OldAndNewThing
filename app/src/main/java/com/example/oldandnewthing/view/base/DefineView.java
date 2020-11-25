package com.example.oldandnewthing.view.base;

import android.app.Activity;
import android.content.Context;

import com.example.oldandnewthing.model.bean.ArticleBean;
import com.example.oldandnewthing.model.bean.PhotoBean;
import com.example.oldandnewthing.model.bean.VideoBean;

import java.util.List;
import java.util.Map;

public interface DefineView {
    void initView();

    void requestRefreshBanner();

    void requestRefreshList();

    void refreshBanner(Object data);

    void refreshList(Object data);

    Activity getContext();

    void requestRefreshArticle(String uri);

    void requestRefershPhoto(String uri);

    void refreshArticle(ArticleBean articleBean);

    void refreshPhoto(PhotoBean photoBean);

    void requestRefreshVideo(String uri);

    void refreshVideo(VideoBean videoBean);

    void requestSearch(String keyword);

    void refreshSearchError();

    void refreshSearchSuccess(List<Map<String,Object>> data);
}
