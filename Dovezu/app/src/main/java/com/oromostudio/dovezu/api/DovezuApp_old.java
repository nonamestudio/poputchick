package com.oromostudio.dovezu.api;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DovezuApp_old extends Application{

    private static DovezuAPI_old dovezuAPI;
    private Retrofit retrofit;
    /////////////////////////////////////////////////////
    //TODO: Delete localUrl. Only dor development period
    private String localUrl = "http://10.0.2.2:3000";
    ////////////////////////////////////////////////////
    private String globalUrl = "https://dovezu.herokuapp.com";

    @Override
    public void onCreate() {
        super.onCreate();

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(localUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        dovezuAPI = retrofit.create(DovezuAPI_old.class);
    }

    public static DovezuAPI_old getAPI(){
        return dovezuAPI;
    }
}
