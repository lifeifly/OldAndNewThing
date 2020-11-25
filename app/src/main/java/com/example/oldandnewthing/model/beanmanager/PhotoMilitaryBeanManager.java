package com.example.oldandnewthing.model.beanmanager;

import com.example.oldandnewthing.model.bean.PhotoMilitaryBean;
import com.example.oldandnewthing.utils.UnicodeUtils;
import com.google.gson.Gson;

public class PhotoMilitaryBeanManager {
    public static PhotoMilitaryBean getData(String html) {
        //将16进制编码转换为中文字符
        String s=html.replace("&amp;", "&")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&ldquo;", "\"")
                .replace("&rdquo;", "\"")
                .replace("&lsquo;", "'")
                .replace("&rsquo;", "'")
                .replace("&hellip;", "...")
                .replace("&mdash;", "-")
                .replace("<br>", "\n")
                .replace("</br>", "")
                .replace("<br></br>", "\n");
        String result = UnicodeUtils.unicodeToString(s);
        //用Gson转换成实体类
        PhotoMilitaryBean photoBean = new Gson().fromJson(result, PhotoMilitaryBean.class);
        return photoBean;
    }
}
