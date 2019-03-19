package com.emam8.emam8_universal.Model;

public class SecFarsiPoem {

    private String id;
    private String title;
    private String count;

    public SecFarsiPoem(String id, String name, String count) {
        this.id = id;
        this.title = name;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTille() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
