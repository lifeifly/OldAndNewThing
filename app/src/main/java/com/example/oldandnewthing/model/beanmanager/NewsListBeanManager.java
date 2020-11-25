package com.example.oldandnewthing.model.beanmanager;

import android.util.Log;

import com.example.oldandnewthing.model.bean.NewsListBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsListBeanManager {
    public static List<NewsListBean> getNewsListData(String html,int startIndex){
        List<NewsListBean> newsListBeanList=new ArrayList<>();
        try {
            String s=html.replace("&amp;", "&")
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
            //因为返回的html数据包含小括号，需要去掉
            String result=html.substring(startIndex,s.length()-1);
            JSONObject jsonObject=new JSONObject(result);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray list = data.getJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                JSONObject jsonObject1=list.getJSONObject(i);
                String brief=jsonObject1.getString("brief");
                String count=jsonObject1.getString("count");
                String ext_field=jsonObject1.getString("ext_field");
                String focus_date=jsonObject1.getString("focus_date");
                String id=jsonObject1.getString("id");
                String image=jsonObject1.getString("image");
                String image2=jsonObject1.getString("image2");
                String image3=jsonObject1.getString("image3");
                String keywords=jsonObject1.getString("keywords");
                String title=jsonObject1.getString("title");
                String url=jsonObject1.getString("url");
                newsListBeanList.add(new NewsListBean(brief,count,ext_field,focus_date,id,image,image2,image3,keywords,title,url));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("12",String.valueOf(newsListBeanList.size()));
        return newsListBeanList;
    }
}
