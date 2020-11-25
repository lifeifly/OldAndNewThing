package com.example.oldandnewthing.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.animation.LinearInterpolator;


import com.example.oldandnewthing.view.ArticleActivity;
import com.example.oldandnewthing.view.PhotoActivity;
import com.example.oldandnewthing.utils.CommonUtils;
import com.example.oldandnewthing.view.VideoActivity;

import java.io.IOException;

public class OpenActivity {
    public static void openActivity(String url, Context context, Intent intent) throws IOException {

        //根据不同数据类型选择打开哪个活动
        String attr = CommonUtils.distinguishAttr(url);
        //后续需要源链接所以传输到相应活动
        intent.putExtra("url", url);
        if (attr.equals("ARTI")) {
            //属于文章类
            intent.setClass(context, ArticleActivity.class);
            context.startActivity(intent);
        } else if (attr.equals("PHOA")) {
            //属于图片类
            intent.setClass(context, PhotoActivity.class);
            context.startActivity(intent);
        }else if (attr.equals("VIDE")){
            //属于视频类
            intent.setClass(context, VideoActivity.class);
            context.startActivity(intent);

        }
    }
}
