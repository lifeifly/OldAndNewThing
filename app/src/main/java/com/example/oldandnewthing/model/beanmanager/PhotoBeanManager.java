package com.example.oldandnewthing.model.beanmanager;

import android.util.Log;

import com.example.oldandnewthing.model.bean.PhotoBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PhotoBeanManager {
    public static PhotoBean getData(String result) {

        PhotoBean photoArticleBean = null;
        Document document = Jsoup.parse(result.replace("&amp;", "&")
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
                .replace("<br></br>", "\n"));
        Element ul = document.select("ul").first();
        String title = ul.attr("title");
        String id = ul.attr("id");
        String nextId = ul.attr("nextid");
        String prevId = ul.attr("previd");

        List<PhotoBean.ListBean> listBeans = new ArrayList<>();
        photoArticleBean = new PhotoBean(listBeans, title, id, nextId, prevId);
        Elements lis = ul.select("li");
        for (Element li : lis) {
            String photoUrl = li.attr("photourl");
            String newBigUrl = li.attr("newbigurl");
            String bigUrl = li.attr("bigurl");
            String smallUrl = li.attr("smallurl");
            String listUrl = li.attr("listurl");
            String listTitle = li.attr("title");
            String listId = li.attr("id");
            String time = li.attr("time");
            String data = li.text();
            listBeans.add(photoArticleBean.new ListBean(photoUrl, newBigUrl, bigUrl, smallUrl, listUrl, listTitle, time, listId, data));
        }
        return photoArticleBean;
    }
}
