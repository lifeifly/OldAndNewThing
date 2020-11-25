package com.example.oldandnewthing.model.beanmanager;

import android.util.Log;

import com.example.oldandnewthing.model.bean.VideoBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class VideoBeanManager {
    public static VideoBean getData(String result) {
        Document parse = Jsoup.parse(result.replace("&amp;", "&")
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
        Element element=parse.select("video").first();
        String header = element.select("header").first().text();
        String keyword = element.select("keywords").first().text();
        Log.d("VIDEO1",header+keyword);
        String brief = element.select("brief").first().text();

        String centerId = element.select("centerId").first().text();
        String editor = element.select("editor").first().text();
        return new VideoBean(header, keyword, editor, brief, centerId);
    }
}
