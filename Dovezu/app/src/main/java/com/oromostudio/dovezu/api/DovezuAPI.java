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

    private static final String LOGIN   = "/login";
    private static final String PROFILE = "/profile";
    private static final String SIGNUP  = "/signup";
    private static final String LOGOUT  = "/logout";

    public static final String COOKIE_NAME = "connect.sid";
    public static final String DOMAIN    = "dovezu.herokuapp.com";




    private static final String SAVE_COOKIE = "myCookie";
    private static final String USERNAME_FIELD = "username";



    private static SharedPreferences pref;



    private static String baseAbsoluteUrl(String url){
        //TODO: change to BASE_URL after tests
        return BASE_URL + url;
    }

    public static AsyncHttpClient getClient(Context context){

        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);

        BasicClientCookie cookie = new BasicClientCookie("cool", "awesome");



        cookieStore.addCookie(cookie);

        client.setCookieStore(cookieStore);



        return client;
    }

    public static void saveSession (Context context, String session){
        pref = context.getSharedPreferences(SAVE_COOKIE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(COOKIE_NAME, session);
        edit.commit();
    }

    public static String getSession(Context context){
        pref = context.getSharedPreferences(SAVE_COOKIE, Context.MODE_PRIVATE);
        return pref.getString(COOKIE_NAME, "");
    }

    public static void clearSession(Context context){
        pref = context.getSharedPreferences(SAVE_COOKIE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.clear();
        edit.commit();
    }






}
