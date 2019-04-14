package com.emam8.emam8_universal.Model;

public class SecFarsiPoem {

    private String id;
    private String title;
    private String count;
    private String ordering;

    public SecFarsiPoem(String id, String title, String count, String ordering) {
        this.id = id;
        this.title = title;
        this.count = count;
        this.ordering = ordering;
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

    public String getOrdering() {
        return ordering;
    }

    public void setOrdering(String ordering) {
        this.ordering = ordering;
    }
}
