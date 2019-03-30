package com.emam8.emam8_universal.Model;

public class Poems {
    String title;
    String sabk;
    String poet;
    String article_id;
    String state;
    String profile;
    String poet_id;
    private boolean isVisited=false;

    public Poems(String title, String sabk, String poet, String article_id, String state,String profile,String poet_id) {
        this.title = title;
        this.sabk = sabk;
        this.poet = poet;
        this.article_id = article_id;
        this.state = state;
        this.profile = profile;
        this.poet_id = poet_id;
    }

    public String getPoet_id() {
        return poet_id;
    }

    public void setPoet_id(String poet_id) {
        this.poet_id = poet_id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSabk() {
        return sabk;
    }

    public void setSabk(String sabk) {
        this.sabk = sabk;
    }

    public String getPoet() {
        return poet;
    }

    public void setPoet(String poet) {
        this.poet = poet;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
