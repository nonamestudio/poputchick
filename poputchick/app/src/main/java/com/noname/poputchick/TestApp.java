package com.noname.poputchick;

import android.app.Application;

import com.noname.poputchick.TestData.TestInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TestApp extends Application {

    private static TestInterface testInterface;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.local_url))//base address part, like http://google.com/
                .addConverterFactory(GsonConverterFactory.create())// Converter from JSON to objects
                .build();

        testInterface = retrofit.create(TestInterface.class);
    }

    public static TestInterface getApi(){
        return testInterface;
    }
}
