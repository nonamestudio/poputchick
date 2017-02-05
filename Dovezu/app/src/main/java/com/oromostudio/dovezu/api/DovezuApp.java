package com.oromostudio.dovezu.api;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DovezuApp extends Application{

    private static DovezuAPI dovezuAPI;
    private Retrofit retrofit;
    private String localUrl = "http://10.0.2.2:3000";
    private String globalUrl = "https://dovezu.herokuapp.com";

    @Override
    public void onCreate() {
        super.onCreate();

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(localUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        dovezuAPI = retrofit.create(DovezuAPI.class);
    }

    public static DovezuAPI getAPI(){
        return dovezuAPI;
    }
}
