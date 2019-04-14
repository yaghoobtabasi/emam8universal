package com.emam8.emam8_universal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Poem_retro {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("body")
    @Expose
    private String body;

    @SerializedName("poet_id")
    @Expose
    private String poet_id;


    @SerializedName("poet_name")
    @Expose
    private String poet_name;

    @SerializedName("sectionid")
    @Expose
    private String sectionid;

    @SerializedName("catid")
    @Expose
    private String catid;

    @SerializedName("sname")
    @Expose
    private String sname;

    @SerializedName("cname")
    @Expose
    private String cname;

    @SerializedName("sabk")
    @Expose
    private String sabk;


    @SerializedName("state")
    @Expose
    private String state;


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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPoet_id() {
        return poet_id;
    }

    public void setPoet_id(String poet_id) {
        this.poet_id = poet_id;
    }

    public String getPoet_name() {
        return poet_name;
    }

    public void setPoet_name(String poet_name) {
        this.poet_name = poet_name;
    }

    public String getSectionid() {
        return sectionid;
    }

    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getSabk() {
        return sabk;
    }

    public void setSabk(String sabk) {
        this.sabk = sabk;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    @Override
    public String toString() {
        return "Poem_retro{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", poet_id='" + poet_id + '\'' +
                ", poet_name='" + poet_name + '\'' +
                ", sectionid='" + sectionid + '\'' +
                ", catid='" + catid + '\'' +
                ", sname='" + sname + '\'' +
                ", cname='" + cname + '\'' +
                ", sabk='" + sabk + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
