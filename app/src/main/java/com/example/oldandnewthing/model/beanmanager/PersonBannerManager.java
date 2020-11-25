package com.example.oldandnewthing.model.beanmanager;

import com.example.oldandnewthing.model.bean.MiPerBannerBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class PersonBannerManager {

    public static List<MiPerBannerBean> getData(String html) {
        List<MiPerBannerBean> list = new ArrayList<>();
        Document document = Jsoup.parse(html.replace("&amp;", "&")
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
        Element renwu = document.select("div.renwu16170_ind01").first();
        Elements li = renwu.select("ul").first().select("li");
        for (Element element : li) {
            Element select = element.select("a").first();
            String href = select.attr("href");
            String imgSrc = select.select("img").first().attr("src");

            Element cover = element.select("div.cover").first().select("a").first();
            String text = cover.text();
            list.add(new MiPerBannerBean(href, text, imgSrc));
        }
        return list;
    }
}