package com.oromostudio.dovezu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileModel {

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("local")
    @Expose
    private LocalModel local;

    @SerializedName("facebook")
    @Expose
    private FacebookModel facebook;

    @SerializedName("twitter")
    @Expose
    private TwitterModel twitter;

    @SerializedName("google")
    @Expose
    private GoogleModel google;

    @SerializedName("vkontakte")
    @Expose
    private VkontakteModel vkontakte;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalModel getLocal() {
        return local;
    }

    public void setLocal(LocalModel local) {
        this.local = local;
    }

    public FacebookModel getFacebook() {
        return facebook;
    }

    public void setFacebook(FacebookModel facebook) {
        this.facebook = facebook;
    }

    public TwitterModel getTwitter() {
        return twitter;
    }

    public void setTwitter(TwitterModel twitter) {
        this.twitter = twitter;
    }

    public GoogleModel getGoogle() {
        return google;
    }

    public void setGoogle(GoogleModel google) {
        this.google = google;
    }

    public VkontakteModel getVkontakte() {
        return vkontakte;
    }

    public void setVkontakte(VkontakteModel vkontakte) {
        this.vkontakte = vkontakte;
    }
}
