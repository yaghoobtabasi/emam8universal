package com.emam8.emam8_universal.Model;

public class CatFarsiPoem {

    private String id;
    private String title;
    private String count;

    public CatFarsiPoem(String id, String title, String count) {
        this.id = id;
        this.title = title;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
