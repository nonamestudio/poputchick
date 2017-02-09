package com.oromostudio.dovezu.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

/**
 * Created by abaik on 07.02.2017.
 */

public class DovezuAPI {


    private static final String BASE_URL  = "https://dovezu.herokuapp.com";
    private static final String LOCAL_URL = "http://192.168.56.1:3000";
    private static final String DOMAIN    = "dovezu.herokuapp.com";

    private static final String LOGIN   = "/login";
    private static final String PROFILE = "/profile";
    private static final String SIGNUP  = "/signup";
    private static final String LOGOUT  = "/logout";

    private static final String COOKIE_NAME = "connect.sid";


    private static final String SAVE_COOKIE = "myCookie";
    private static final String USERNAME_FIELD = "username";

    private static final String EMAIL_FIELD    = "email";
    private static final String PHONE_FIELD    = "phone";
    private static final String PASSWORD_FIELD = "password";



    private static String baseAbsoluteUrl(String url){
        //TODO: change to BASE_URL after tests
        return BASE_URL + url;
    }

    public static AsyncHttpClient getClient(Context context){

        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);

        BasicClientCookie cookie = new BasicClientCookie("cool", "awesome");

        SharedPreferences pref = context.getSharedPreferences(getSaveCookie(), Context.MODE_PRIVATE);

        cookie.setValue(pref.getString(getSaveCookie(), ""));

        cookieStore.addCookie(cookie);

        client.setCookieStore(cookieStore);



        return client;
    }

    public static String getUsernameField() {
        return USERNAME_FIELD;
    }

    public static String getEmailField() {
        return EMAIL_FIELD;
    }

    public static String getPhoneField() {
        return PHONE_FIELD;
    }

    public static String getPasswordField() {
        return PASSWORD_FIELD;
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

    public static String getDomain() {
        return DOMAIN;
    }

    public static String getLogout() {
        return baseAbsoluteUrl(LOGOUT);
    }





}
