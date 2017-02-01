package com.noname.poputchick.TestData;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TestInterface {

    @GET("api/getusers")
    Call<List<TestData>> getUsers();

    @POST("api/adduser")
    Call<TestData> addUser(@Body TestData td);

    @POST("api/removeuser")
    Call<TestData> removeUser(@Body TestData td);

    @POST("api/updateuser")
    Call<TestData> updateUser(@Body TestData td);

}
