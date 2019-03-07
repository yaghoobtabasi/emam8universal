package com.emam8.emam8_universal.Model;

public class Sections {
    String title;
    String sectionid;
    String count;

    public Sections(String title, String sectionid, String count) {
        this.title = title;
        this.sectionid = sectionid;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSectionid() {
        return sectionid;
    }

    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
