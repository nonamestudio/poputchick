package com.oromostudio.dovezu.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.oromostudio.dovezu.library.Constants;

import java.util.List;

import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;


public class DovezuClient {
    private static final String BASE_URL = "https://dovezu.herokuapp.com";
    private static final String LOCAL_URL = "http://192.168.0.106:3000";

    private static final String LOGIN = "/login";
    private static final String SIGNUP = "/signup";
    private static final String PROFILE = "/profile";
    private static final String LOGOUT = "/logout";
    private static final String AUTH_CHECK = "/checkAuth";

    private static final String REQUESTS_API = "/api/requests";


    private static AsyncHttpClient client = new AsyncHttpClient();


    //**********************************************************************************************

    private static String absoluteUrl(String url){
        return BASE_URL.concat(url);
    }

    public static RequestHandle checkAuth(Context context, ResponseHandlerInterface responseHandler){
        client.setCookieStore(getCookieStore(context));
        return client.get(context, absoluteUrl(AUTH_CHECK), responseHandler);
    }

    public static RequestHandle login(Context context, PersistentCookieStore cookieStore, RequestParams params, ResponseHandlerInterface responseHandler){
        client.setCookieStore(cookieStore);
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

    public static RequestHandle addRequest(Context context, RequestParams params, ResponseHandlerInterface responseHandler){
        String url = absoluteUrl(REQUESTS_API + "/user/" + getUserID(context));
        client.setCookieStore(getCookieStore(context));
        return client.post(context, url, params, responseHandler);
    }

    public static RequestHandle getRequests(Context context, ResponseHandlerInterface responseHandler){
        client.setCookieStore(getCookieStore(context));
        return client.get(context, absoluteUrl(REQUESTS_API), responseHandler);
    }

    //**********************************************************************************************

    private static PersistentCookieStore getCookieStore(Context context){
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        cookieStore.addCookie(getCookie(context));
        return cookieStore;
    }

    private static PersistentCookieStore getEmptyCookieStore(Context context){
        return new PersistentCookieStore(context);
    }

    private static BasicClientCookie getCookie(Context context){
        BasicClientCookie cookie = new BasicClientCookie("android", "client");
        cookie.setValue(getSession(context));
        return cookie;
    }

    //**********************************************************************************************

    private static String getSession(Context context){
        SharedPreferences pref = context.getSharedPreferences(Constants.SAVE_COOKIE, Context.MODE_PRIVATE);
        return pref.getString(Constants.SESSION, "");
    }

    private static void setSession(Context context, String session){
        SharedPreferences pref = context.getSharedPreferences(Constants.SAVE_COOKIE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(Constants.SESSION, session);
        edit.apply();
    }

    private static void clearPrefs(Context context, String name){
        SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.clear();
        edit.apply();
    }

    public static void clearSession(Context context){
        clearPrefs(context, Constants.SAVE_COOKIE);
        clearPrefs(context, Constants.USER_DATA);
    }

    public static Boolean checkSession(Context context){
        SharedPreferences pref = context.getSharedPreferences(Constants.SAVE_COOKIE, Context.MODE_PRIVATE);
        return pref.contains(Constants.SESSION);
    }

    public static void saveSession(Context context){
        PersistentCookieStore cookieStore = getEmptyCookieStore(context);
        SharedPreferences pref = context.getSharedPreferences(Constants.SAVE_COOKIE, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = pref.edit();
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().compareTo(Constants.COOKIE_NAME) == 0) {
                if (cookie.getDomain().compareTo(Constants.DOMAIN) == 0) {
                    ed.putString(Constants.SESSION, cookie.getValue());
                    ed.apply();
                    break;
                }
            }
        }
    }

    //**********************************************************************************************

    private static String getUserID(Context context){
        SharedPreferences pref = context.getSharedPreferences(Constants.USER_DATA, Context.MODE_PRIVATE);
        return pref.getString(Constants.USER_ID, "");
    }
}
