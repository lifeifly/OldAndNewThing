package com.example.oldandnewthing.model.bean;

import java.io.Serializable;
import java.util.List;

//图片新闻文章信息实体类
public class PhotoBean implements Serializable {
    private List<ListBean> databean;
    private String title;
    private String id;
    private String nextId;
    private String prevId;

    public PhotoBean(List<ListBean> databean, String title, String id, String nextId, String prevId) {
        this.databean = databean;
        this.title = title;
        this.id = id;
        this.nextId = nextId;
        this.prevId = prevId;
    }

    public List<ListBean> getDatabean() {
        return databean;
    }

    public void setDatabean(List<ListBean> databean) {
        this.databean = databean;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNextId() {
        return nextId;
    }

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }

    public String getPrevId() {
        return prevId;
    }

    public void setPrevId(String prevId) {
        this.prevId = prevId;
    }

   public class ListBean {
        private String photoUrl;
        private String newBigUrl;
        private String bigUrl;
        private String smallUrl;
        private String listUrl;
        private String lidtTitle;
        private String time;
        private String listId;
        private String des;

       public ListBean(String photoUrl, String newBigUrl, String bigUrl, String smallUrl, String listUrl, String lidtTitle, String time, String listId, String des) {
           this.photoUrl = photoUrl;
           this.newBigUrl = newBigUrl;
           this.bigUrl = bigUrl;
           this.smallUrl = smallUrl;
           this.listUrl = listUrl;
           this.lidtTitle = lidtTitle;
           this.time = time;
           this.listId = listId;
           this.des = des;
       }

       public String getPhotoUrl() {
           return photoUrl;
       }

       public void setPhotoUrl(String photoUrl) {
           this.photoUrl = photoUrl;
       }

       public String getNewBigUrl() {
           return newBigUrl;
       }

       public void setNewBigUrl(String newBigUrl) {
           this.newBigUrl = newBigUrl;
       }

       public String getBigUrl() {
           return bigUrl;
       }

       public void setBigUrl(String bigUrl) {
           this.bigUrl = bigUrl;
       }

       public String getSmallUrl() {
           return smallUrl;
       }

       public void setSmallUrl(String smallUrl) {
           this.smallUrl = smallUrl;
       }

       public String getListUrl() {
           return listUrl;
       }

       public void setListUrl(String listUrl) {
           this.listUrl = listUrl;
       }

       public String getLidtTitle() {
           return lidtTitle;
       }

       public void setLidtTitle(String lidtTitle) {
           this.lidtTitle = lidtTitle;
       }

       public String getTime() {
           return time;
       }

       public void setTime(String time) {
           this.time = time;
       }

       public String getListId() {
           return listId;
       }

       public void setListId(String listId) {
           this.listId = listId;
       }

       public String getDes() {
           return des;
       }

       public void setDes(String des) {
           this.des = des;
       }
   }
}
