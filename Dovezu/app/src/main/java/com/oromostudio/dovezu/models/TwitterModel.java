package com.oromostudio.dovezu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TwitterModel {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("displayName")
    @Expose
    private String displayName;

    @SerializedName("username")
    @Expose
    private String username;

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
