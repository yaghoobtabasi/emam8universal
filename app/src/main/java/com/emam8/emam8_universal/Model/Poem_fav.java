package com.emam8.emam8_universal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Poem_fav {

    @SerializedName("article_id")
    @Expose
    private String article_id;

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("mode")
    @Expose
    private String mode;


    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
