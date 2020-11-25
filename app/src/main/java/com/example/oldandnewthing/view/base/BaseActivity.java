package com.example.oldandnewthing.view.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.oldandnewthing.R;
import com.example.oldandnewthing.model.bean.ArticleBean;
import com.example.oldandnewthing.model.bean.PhotoBean;
import com.example.oldandnewthing.model.bean.VideoBean;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class BaseActivity extends FragmentActivity implements DefineView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //打开其他Activity
    protected void openActivity(Class<?> pClass) {
        Intent intent = new Intent(this, pClass);
        startActivity(intent);
    }

    //计算StatusBar的高度
    public static int getStatusBarHeight(Context context) {

        Class<?> c = null;

        Object obj = null;

        Field field = null;

        int x = 0, statusBarHeight = 0;

        try {

            c = Class.forName("com.android.internal.R$dimen");

            obj = c.newInstance();

            field = c.getField("status_bar_height");

            x = Integer.parseInt(field.get(obj).toString());

            statusBarHeight = context.getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {

            e1.printStackTrace();

        }

        return statusBarHeight;

    }

    @Override
    public void initView() {

    }

    @Override
    public void requestRefreshBanner() {

    }

    @Override
    public void requestRefreshList() {

    }

    @Override
    public void refreshBanner(Object data) {

    }

    @Override
    public void refreshList(Object data) {

    }

    @Override
    public Activity getContext() {
        return null;
    }

    @Override
    public void requestRefreshArticle(String uri) {

    }

    @Override
    public void requestRefershPhoto(String uri) {

    }

    @Override
    public void refreshArticle(ArticleBean articleBean) {

    }

    @Override
    public void refreshPhoto(PhotoBean photoBean) {

    }

    @Override
    public void requestRefreshVideo(String uri) {

    }

    @Override
    public void refreshVideo(VideoBean videoBean) {

    }

    @Override
    public void requestSearch(String keyword) {

    }

    @Override
    public void refreshSearchError() {

    }

    @Override
    public void refreshSearchSuccess(List<Map<String, Object>> data) {

    }


}
