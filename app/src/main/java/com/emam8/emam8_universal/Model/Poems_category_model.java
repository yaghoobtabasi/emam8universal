package com.emam8.emam8_universal.Model;

public class Poems_category_model {
    String title;
    String catid;
    String total;

    public Poems_category_model(String title, String catid, String total) {
        this.title = title;
        this.catid = catid;
        this.total = total;
    }

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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
