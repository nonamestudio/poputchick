package com.oromostudio.dovezu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VkontakteModel {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("displayName")
    @Expose
    private String displayName;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("profileURL")
    @Expose
    private String profileURL;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }
}
