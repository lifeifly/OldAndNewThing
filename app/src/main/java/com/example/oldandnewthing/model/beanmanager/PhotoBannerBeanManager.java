package com.example.oldandnewthing.model.beanmanager;

import android.util.Log;

import com.example.oldandnewthing.model.bean.BannerBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PhotoBannerBeanManager {
    public static BannerBean getData(String html){
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
        Element show=document.select("div.show").first();
        //图片部分
        Element image=show.select("div.image").first();
        Element a=image.select("a").first();
        String href=a.attr("href");
        String imageSrc=a.select("img").first().attr("class");
        //文本部分
        Element box=show.select("div.box").first();
        Element h3=box.select("h3").first();
        String title=h3.text();
        String content=box.select("p").first().text();
        BannerBean bannerBean=new BannerBean(href,imageSrc,title,content);
        return bannerBean;
    }
}
