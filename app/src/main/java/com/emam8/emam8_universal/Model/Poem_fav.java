package com.emam8.emam8_universal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Poem_fav {

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("result")
    @Expose
    private String result;

    @SerializedName("success")
    @Expose
    private String success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "Poem_fav{" +
                "msg='" + msg + '\'' +
                ", result='" + result + '\'' +
                ", success='" + success + '\'' +
                '}';
    }
}
