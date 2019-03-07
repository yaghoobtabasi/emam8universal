package com.emam8.emam8_universal.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Sgin in Requst model used in sign in request
 */
public class SignInRequestModel {



    @SerializedName("username")
    @Expose
    public String username;

    @SerializedName("password")
    @Expose
    public String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignInRequestModel{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
