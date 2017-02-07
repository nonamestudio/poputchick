package com.oromostudio.dovezu.api;

/**
 * Created by abaik on 07.02.2017.
 */

public class DovezuAPI {


    private static final String BASE_URL = "https://dovezu.herokuapp.com";
    private static final String LOCAL_URL = "http://10.0.2.2:3000";

    private static final String LOGIN = "/login";
    private static final String PROFILE = "/profile";
    private static final String SIGNUP = "/signup";


    private static final String COOKIE_NAME = "set-cookie";
    private static final String SAVE_COOKIE = "connect.id";

    private static String baseAbsoluteUrl(String url){
        return LOCAL_URL + url;
    }

    public static String getSaveCookie() {
        return SAVE_COOKIE;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getLocalUrl() {
        return LOCAL_URL;
    }

    public static String getLogin() {
        return baseAbsoluteUrl(LOGIN);
    }

    public static String getSignup() {
        return baseAbsoluteUrl(SIGNUP);
    }


    public static String getProfile() {
        return baseAbsoluteUrl(PROFILE);
    }

    public static String getCookieName() {
        return COOKIE_NAME;
    }





}
