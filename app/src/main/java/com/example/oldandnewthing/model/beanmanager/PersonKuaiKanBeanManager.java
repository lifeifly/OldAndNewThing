package com.example.oldandnewthing.model.beanmanager;

import android.util.Log;

import com.example.oldandnewthing.model.bean.PersonKuaiKanBean;
import com.google.gson.Gson;

public class PersonKuaiKanBeanManager {
    public static PersonKuaiKanBean getData(String json,int startIndex) {
        String s=json.replace("&amp;", "&")
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
        PersonKuaiKanBean bean = new Gson().fromJson(s, PersonKuaiKanBean.class);

        return bean;
    }
}
