package com.oromostudio.dovezu.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.sax.RootElement;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;


public class DovezuClient {

    private static final String SAVE_COOKIE = "cookie";
    private static final String SESSION = "session";

    private static final String BASE_URL = "https://dovezu.herokuapp.com";
    private static final String LOGIN = "/login";
    private static final String SIGNUP = "/signup";
    private static final String PROFILE = "/profile";
    private static final String LOGOUT = "/logout";


    private static AsyncHttpClient client = new AsyncHttpClient();


    //**********************************************************************************************

    private static String absoluteUrl(String url){
        return BASE_URL.concat(url);
    }

    public static RequestHandle login(Context context, RequestParams params, ResponseHandlerInterface responseHandler){
        client.setCookieStore(getEmptyCookieStore(context));
        return client.post(context, absoluteUrl(LOGIN), params, responseHandler);
    }

    public static RequestHandle signup(Context context, RequestParams params, ResponseHandlerInterface responseHandler){
        client.setCookieStore(getEmptyCookieStore(context));
        return client.post(context, absoluteUrl(SIGNUP), params, responseHandler);
    }

    public static RequestHandle profile(Context context, ResponseHandlerInterface responseHandler){
        client.setCookieStore(getCookieStore(context));
        return client.get(context, absoluteUrl(PROFILE), responseHandler);
    }

    public static RequestHandle logout(Context context, ResponseHandlerInterface responseHandler){
        client.setCookieStore(getCookieStore(context));
        return client.get(context, absoluteUrl(LOGOUT), responseHandler);
    }

    //**********************************************************************************************

    private static PersistentCookieStore getCookieStore(Context context){
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        cookieStore.addCookie(getCookie(context));
        return cookieStore;
    }

    private static PersistentCookieStore getEmptyCookieStore(Context context){
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        return cookieStore;
    }

    private static BasicClientCookie getCookie(Context context){
        BasicClientCookie cookie = new BasicClientCookie("android", "client");
        cookie.setValue(getSession(context));
        return cookie;
    }

    //**********************************************************************************************

    private static String getSession(Context context){
        SharedPreferences pref = context.getSharedPreferences(SAVE_COOKIE, Context.MODE_PRIVATE);
        return pref.getString(SESSION, "");
    }

    private static void setSession(Context context, String session){
        SharedPreferences pref = context.getSharedPreferences(SAVE_COOKIE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(SESSION, session);
        edit.commit();
    }

    public static void clearSession(Context context){
        SharedPreferences pref = context.getSharedPreferences(SAVE_COOKIE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.clear();
        edit.commit();
    }

    public static void saveSession(Context context){

    }

    //**********************************************************************************************

}
