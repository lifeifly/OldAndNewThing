package com.example.oldandnewthing.model.beanmanager;


import android.util.Log;

import com.example.oldandnewthing.model.bean.ArticleBean;
import com.example.oldandnewthing.utils.CommonUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleBeanManager {
    public static ArticleBean getData(String url) throws IOException {
        Document document = Jsoup.parse(new URL(url), 10000);
        //CONTENT中xml格式中一些字符需要转变
        Document document1 = Jsoup.parse(String.valueOf(document)
                .replace("&amp;", "&")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&ldquo;", "\"")
                .replace("&rdquo;", "\"")
                .replace("&lsquo;", "'")
                .replace("&rsquo;", "'")
                .replace("&hellip;", "...")
                .replace("&mdash;", "-")
                .replace("<br>", "\n")
                .replace("<br/>", "")
                .replace("<br></br>", "\n")
        );
        String videourl = "";
        //除了CONTENT的其他部分
        String title = document.select("TITLE").first().text();
        String keyword = document.select("KEYWORD").first().text();
        String source = document.select("SOURCE").first().text();
        String time = document.select("DATE").first().text();
        String des = document.select("DESC").first().text();
        String editor = document.select("EDITOR").first().text();
        //video资源链接和List<Map>部分
        Elements elements = document1.select("p");
        List<Map<String, String>> data = new ArrayList<>();
        for (Element element : elements) {
            Map<String, String> value = new HashMap<>();
            Element img = element.select("p[style=\"text-align: center;\"]").first();
            if (img != null) {
                if (img.select("img").first() != null) {
                    String img1 = element.select("img").first().attr("src");
                    value.put("img", img1);
                } else {
                    value.put("imgdes", img.text());
                }
            } else if (element.select("strong").first() != null) {
                String bigTtile = element.text();
                value.put("bigtitle", bigTtile);
            } else {
                String text = element.text();
                if (!text.contains("newPlayer") && !text.contains("news")) {
                    value.put("text", text);
                } else {
                    videourl = CommonUtils.videoUrl(text);
                }
            }
            data.add(value);
        }

        return new ArticleBean(title, des, source, time, videourl, editor, data, keyword);
    }
}
