package com.example.oldandnewthing.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewsListBean implements Serializable {
       private String brief;
       private String count;
       @SerializedName("ext_field")
       private String field;
       @SerializedName("focus_date")
       private String date;
       private String id;
       private String image;
       private String image2;
       private String image3;
       private String keywords;
       private String title;
       private String url;

       public NewsListBean(String brief, String count, String field, String date, String id, String image, String image2, String image3, String keywords, String title, String url) {
              this.brief = brief;
              this.count = count;
              this.field = field;
              this.date = date;
              this.id = id;
              this.image = image;
              this.image2 = image2;
              this.image3 = image3;
              this.keywords = keywords;
              this.title = title;
              this.url = url;
       }

       public String getBrief() {
              return brief;
       }

       public void setBrief(String brief) {
              this.brief = brief;
       }

       public String getCount() {
              return count;
       }

       public void setCount(String count) {
              this.count = count;
       }

       public String getField() {
              return field;
       }

       public void setField(String field) {
              this.field = field;
       }

       public String getDate() {
              return date;
       }

       public void setDate(String date) {
              this.date = date;
       }

       public String getId() {
              return id;
       }

       public void setId(String id) {
              this.id = id;
       }

       public String getImage() {
              return image;
       }

       public void setImage(String image) {
              this.image = image;
       }

       public String getImage2() {
              return image2;
       }

       public void setImage2(String image2) {
              this.image2 = image2;
       }

       public String getImage3() {
              return image3;
       }

       public void setImage3(String image3) {
              this.image3 = image3;
       }

       public String getKeywords() {
              return keywords;
       }

       public void setKeywords(String keywords) {
              this.keywords = keywords;
       }

       public String getTitle() {
              return title;
       }

       public void setTitle(String title) {
              this.title = title;
       }

       public String getUrl() {
              return url;
       }

       public void setUrl(String url) {
              this.url = url;
       }
}
