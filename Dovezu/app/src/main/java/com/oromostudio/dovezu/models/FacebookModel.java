package com.oromostudio.dovezu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacebookModel {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
