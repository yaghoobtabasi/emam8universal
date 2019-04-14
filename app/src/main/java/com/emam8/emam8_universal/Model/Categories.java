package com.emam8.emam8_universal.Model;

public class Categories {
    String title;
    String catid;
    String count;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Categories(String title, String catid, String count) {
        this.title = title;
        this.catid = catid;
        this.count = count;
    }



}
