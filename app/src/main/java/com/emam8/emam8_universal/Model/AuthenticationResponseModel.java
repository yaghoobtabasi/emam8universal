package com.emam8.emam8_universal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Authentication response Model used as response model in sign in and sign up request
 */
public class AuthenticationResponseModel {
//    public TokenModel token;
//    public UserModel user_profile;
    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("access_token")
    @Expose
    public String access_token;
    @SerializedName("user_id")
    @Expose
    public String user_id;
    @SerializedName("expire_in_sec")
    @Expose
    public String expire_in_sec;
    @SerializedName("expire_at")
    @Expose
    public String expire_at;
    @SerializedName("refresh_token")
    @Expose
    public String refresh_token;
    @SerializedName("app_id")
    @Expose
    public String app_id;

    @Override
    public String toString() {
        return "AuthenticationResponseModel{" +
                "success=" + success +
                ", access_token='" + access_token + '\'' +
                ", user_id='" + user_id + '\'' +
                ", expire_in_sec=" + expire_in_sec +
                ", expire_at=" + expire_at +
                ", refresh_token='" + refresh_token + '\'' +
                ", app_id='" + app_id + '\'' +
                '}';
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getExpire_in_sec() {
        return expire_in_sec;
    }

    public void setExpire_in_sec(String expire_in_sec) {
        this.expire_in_sec = expire_in_sec;
    }

    public String getExpire_at() {
        return expire_at;
    }

    public void setExpire_at(String expire_at) {
        this.expire_at = expire_at;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

}
