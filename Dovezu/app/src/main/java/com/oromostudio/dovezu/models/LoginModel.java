package com.oromostudio.dovezu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LoginModel {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword(){
        return password;
    }
}
