package com.oromostudio.dovezu.api;

import com.oromostudio.dovezu.models.LoginModel;
import com.oromostudio.dovezu.models.ProfileModel;
import com.oromostudio.dovezu.models.SignUpModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DovezuAPI {

    ///////////////////////////////////////////////////////
    ///LOCAL SCOPE
    ///////////////////////////////////////////////////////

    //Login local
    @POST("/login")
    Call<String> loginLocal(@Body LoginModel loginModel);

    //Sign up local
    @POST("/signup")
    Call<String> signUpLocal(@Body SignUpModel signUpModel);

    //Connect local
    @POST("/connect/local")
    Call<String> connectLocal(@Body SignUpModel signUpModel);

    //Unlink local
    @GET("/unlink/local")
    Call<String> unlinkLocal();

    ///////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////
    ///FACEBOOK SCOPE
    ///////////////////////////////////////////////////////

    //Authenticate via Facebook
    @GET("/auth/facebook")
    Call<String> loginFacebook();

    //Connect Facebook account
    @GET("/connect/facebook")
    Call<String> connectFacebook();

    //Unlink Facebook account
    @GET("/unlink/facebook")
    Call<String> unlinkFacebook();

    ///////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////
    ///TWITTER SCOPE
    ///////////////////////////////////////////////////////

    //Authenticate via Twitter
    @GET("/auth/twitter")
    Call<String> loginTwitter();

    //Connect Twitter account
    @GET("/connect/twitter")
    Call<String> connectTwitter();

    //Unlink Twitter account
    @GET("/unlink/twitter")
    Call<String> unlinkTwitter();

    ///////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////
    ///GOOGLE SCOPE
    ///////////////////////////////////////////////////////

    //Authenticate via Google
    @GET("/auth/google")
    Call<String> loginGoogle();

    //Connect Google account
    @GET("/connect/google")
    Call<String> connectGoogle();

    //Unlink Google account
    @GET("/unlink/google")
    Call<String> unlinkGoogle();

    ///////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////
    ///VKONTAKTE SCOPE
    ///////////////////////////////////////////////////////

    //Authenticate via Vkontakte
    @GET("/auth/vkontakte")
    Call<String> loginVkontakte();

    //Connect Vkontakte account
    @GET("/connect/vkontakte")
    Call<String> connectVkontakte();

    //Unlink Vkontakte account
    @GET("/unlink/vkontakte")
    Call<String> unlinkVkontakte();

    ///////////////////////////////////////////////////////

    //Get profile
    @GET("/profile")
    Call<ProfileModel> getProfile();
}
