package com.example.oldandnewthing.model.beanmanager;

import android.util.Log;

import com.example.oldandnewthing.model.bean.BannerBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.util.ArrayList;
import java.util.List;

public class BannerBeanManager {
    public static List<BannerBean> getBannerData(String html) {
        List<BannerBean> bannerBeanList = new ArrayList<>();
        Document document = Jsoup.parse(html.replace("&amp;", "&")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&ldquo;", "\"")
                .replace("&rdquo;", "\"")
                .replace("&lsquo;", "'")
                .replace("&rsquo;", "'")
                .replace("&hellip;", "...")
                .replace("&mdash;", "-")
                .replace("<br>","\n")
                .replace("<br/>","")
                .replace("<br></br>", "\n"));

        Element select = document.select("div.list_con").first();
        Elements selects = select.select("div.silde");

        for (Element element : selects) {
            String srcHref = "";
            //图片信息和连接信息
            Element image = element.select("div.image").first();
            Element a = image.select("a").first();
            String href = a.attr("abs:href");
            Element img = a.select("img.lazy").first();
            //图片有两种Tag需要分辨
            if (img.attr("data-echo") != null && img.attr("data-echo").length() > 0) {
                srcHref = img.attr("data-echo");

            } else {
                srcHref = img.attr("data-src");

            }
            Log.d(String.valueOf(1), String.valueOf(srcHref));
//标题和文本信息
            Element right_text = element.select("div.right_text").first();
            Element h3 = right_text.select("h3").first().select("a").first();
            String title = h3.text();
            Element p = right_text.select("p").first().select("a").first();
            String text = p.text();
            bannerBeanList.add(new BannerBean(href, srcHref, title, text));
        }

        return bannerBeanList;
    }
}
