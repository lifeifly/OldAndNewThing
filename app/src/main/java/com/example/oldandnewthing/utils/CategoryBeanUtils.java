package com.example.oldandnewthing.utils;

import com.example.oldandnewthing.model.bean.CategoryBean;

import java.util.ArrayList;
import java.util.List;

//首页分类信息工具类
public class CategoryBeanUtils {

    public static List<CategoryBean> getData() {
        List<CategoryBean> categoryBeans = new ArrayList<>();
        categoryBeans.add(new CategoryBean("https://news.cctv.com/", "新闻"));
        categoryBeans.add(new CategoryBean("https://news.cctv.com/china/", "国内"));
        categoryBeans.add(new CategoryBean("https://news.cctv.com/world/", "国际"));
        categoryBeans.add(new CategoryBean("https://news.cctv.com/society/", "社会"));
        categoryBeans.add(new CategoryBean("https://news.cctv.com/law/", "法治"));
        categoryBeans.add(new CategoryBean("https://photo.cctv.com/mobile/", "图片"));
        categoryBeans.add(new CategoryBean("https://news.cctv.com/ent/", "文娱"));
        categoryBeans.add(new CategoryBean("https://news.cctv.com/tech/", "科技"));
        categoryBeans.add(new CategoryBean("https://news.cctv.com/life/", "生活"));
        categoryBeans.add(new CategoryBean("http://people.cctv.com/", "人物"));
        categoryBeans.add(new CategoryBean("http://military.cctv.com/", "军事"));
        categoryBeans.add(new CategoryBean("https://news.cctv.com/kuaikan/", "快看"));
        return categoryBeans;
    }
}
