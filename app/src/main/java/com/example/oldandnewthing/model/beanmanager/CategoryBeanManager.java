package com.example.oldandnewthing.model.beanmanager;

import android.util.Log;

import com.example.oldandnewthing.model.bean.CategoryBean;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CategoryBeanManager {
    public static List<CategoryBean> getCategoryData(String html) {
        List<CategoryBean> categoryBeanList = new ArrayList<>();
        if (html != null) {
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
            Element select = document.select("div.left").first();
            //分类节点
            Elements categoryNode = select.select("span.nav_tit").select("a");
            for (Element element : categoryNode) {
                String title = element.text();
                String href = element.attr("abs:href");
                categoryBeanList.add(new CategoryBean(title, href));
                Log.d("tag", title + href);
            }
        }
        return categoryBeanList;
    }
}
