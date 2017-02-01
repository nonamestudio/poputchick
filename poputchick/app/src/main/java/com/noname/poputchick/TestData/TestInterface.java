package com.noname.poputchick.TestData;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TestInterface {

    @GET("getusers")
    Call<List<TestData>> getUsers();

    @POST("adduser")
    Call<TestData> addUser(@Body TestData testData);

}
