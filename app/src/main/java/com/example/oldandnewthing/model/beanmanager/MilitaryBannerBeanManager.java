package com.example.oldandnewthing.model.beanmanager;

import com.example.oldandnewthing.model.bean.MiPerBannerBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MilitaryBannerBeanManager {
    public static MiPerBannerBean getData(String html) {
        String href = "";
        String src = "";
        String des = "";
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
        Element content = document.select("div.content").first();
        Elements uls = content.select("ul");
        for (Element ul : uls) {
            if (ul.className().equals("img")) {
                href = ul.select("a").first().attr("href");
                src = ul.select("a").first().select("img").first().attr("src");
            } else {
                des = ul.select("a").text();
            }
        }
        MiPerBannerBean bean = new MiPerBannerBean(href, des, src);
        return bean;
    }
}
