package com.example.oldandnewthing.utils;

import com.example.oldandnewthing.model.url.MainURL;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
    //将毫秒转换为yyyy-MM-dd
    public static String miToDate(String mil) {
        Long date = Long.parseLong(mil);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        String format = simpleDateFormat.format(new Date(date));
        //截成所需的格式
        String s = format.substring(0, 10);
        return s;
    }

    //将源链接变成资源链接
    public static String changeHref(String href, String replace) {
        String url=href;
        if (href.contains("?")) {
            //包含尾部部分，String不能以?截取，将？替换为-
            String replace1 = href.replace("?", "-");
            url=replace1.split("-")[0];
        }
        int lastIndexOf = url.lastIndexOf(".");
        String changedHref = url.substring(0, lastIndexOf +1) + replace;
        return changedHref;
    }

    //从链接中获取信息属性文章还是图片
    public static String distinguishAttr(String url) {
        String attr = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("/") + 5);
        return attr;
    }

    //给字符串中部分字符换成所指示的
    public static String replaceString(String url, String replaced, String replace) {
        String replace1 = url.replace(replaced, replace);
        return replace1;
    }

    //将指定的包含viedoId例如[!--begin:htmlVideoCode--]9eeb18fbd84044249a6f2c413b8c575e,0,1,16:9,newPlayer[!--end:htmlVideoCode--]的字符串，截取videoId并拼接成视频资源链接
    public static String videoUrl(String container) {
        int i = container.indexOf(",");
        int j = container.indexOf("]");
        String substring = container.substring(j + 1, i);
        String realVideoUrl = MainURL.VIDEOURL + substring;
        return realVideoUrl;
    }
}
