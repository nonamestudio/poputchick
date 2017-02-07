package com.oromostudio.dovezu;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.oromostudio.dovezu.api.DovezuAPI;
import com.oromostudio.dovezu.api.DovezuApp_old;
import com.oromostudio.dovezu.models.LocalModel;
import com.oromostudio.dovezu.models.ProfileModel;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    //////////////////////////////
    //Local view elements
    //////////////////////////////
    private View localProfile;
    private TextView localUsername;
    private TextView localEmail;
    private TextView localPhone;
    private TextView localPassword;
    ///////////////////////////////

    ///////////////////////////////
    //Facebook view elements
    ///////////////////////////////
    private View facebookProfile;
    private TextView facebookId;
    private TextView facebookToken;
    private TextView facebookName;
    ///////////////////////////////

    ///////////////////////////////
    //Twitter view elements
    ///////////////////////////////
    private View twitterProfile;
    private TextView twitterId;
    private TextView twitterToken;
    private TextView twitterDisplayName;
    private TextView twitterUsername;
    ///////////////////////////////

    ///////////////////////////////
    //Google view elements
    ///////////////////////////////
    private View googleProfile;
    private TextView googleId;
    private TextView googleToken;
    private TextView googleEmail;
    private TextView googleName;
    //////////////////////////////

    //////////////////////////////
    //Vkontakte view elements
    //////////////////////////////
    private View vkontakteProfile;
    private TextView vkontakteId;
    private TextView vkontakteToken;
    private TextView vkontakteUsername;
    private TextView vkontakteDisplayName;
    private TextView vkontakteGender;
    private TextView vkontakteProfileURL;
    /////////////////////////////


    private ProfileModel profile;
    private SharedPreferences sharedPreferences;
    private AsyncHttpClient client;
    private RequestParams params;
    private PersistentCookieStore cookieStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



    }

    @Override
    protected void onResume() {
        super.onResume();

        getProfile();


    }

    private void initViews() {

        if(profile != null){
            if(profile.getLocal() != null){
                initLocal(profile.getLocal());
            }

//            if(profile.getFacebook() != null){
//                initFacebook(profile.getFacebook());
//            }
//
//            if(profile.getTwitter() != null){
//                initTwitter(profile.getTwitter());
//            }
//
//            if(profile.getGoogle() != null){
//                initGoogle(profile.getGoogle());
//            }
//
//            if(profile.getVkontakte() != null){
//                initVkontakte(profile.getVkontakte());
//            }
        }


    }

    private void getProfile() {


        client = new AsyncHttpClient();
        cookieStore = new PersistentCookieStore(getApplicationContext());
        client.setCookieStore(cookieStore);
        sharedPreferences = getPreferences(MODE_PRIVATE);

        BasicClientCookie cookie = new BasicClientCookie("cook", "awesome");
        cookie.setValue(sharedPreferences.getString(DovezuAPI.getSaveCookie(), ""));
        cookieStore.addCookie(cookie);

        client.get(DovezuAPI.getProfile(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new Gson();
                profile = gson.fromJson(response.toString(), ProfileModel.class);
                initViews();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getApplicationContext(), getString(R.string.failure), Toast.LENGTH_SHORT).show();
            }
        });

        initViews();

        DovezuApp_old.getAPI().getProfile().enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                Toast.makeText(ProfileActivity.this,"Profile loading...", Toast.LENGTH_SHORT).show();
                profile = response.body();
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                Toast.makeText(ProfileActivity.this,"Failure on load profile", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Init local elements
    private void initLocal(LocalModel local) {
        localProfile = findViewById(R.id.localLayoutInclude);

        localUsername = (TextView) localProfile.findViewById(R.id.localUsernameTV);
        localEmail    = (TextView) localProfile.findViewById(R.id.localEmailTV);
        localPhone    = (TextView) localProfile.findViewById(R.id.localPhoneTV);
        localPassword = (TextView) localProfile.findViewById(R.id.localPasswordTV);

        localUsername.setText(local.getUsername());
        localEmail.setText(local.getEmail());
        localPhone.setText(local.getPhone());
        localPassword.setText(local.getPassword());
    }

//    //Init Facebook elements
//    private void initFacebook(FacebookModel facebook){
//        facebookProfile = findViewById(R.id.facebookLayoutInclude);
//
//        facebookId    = (TextView) facebookProfile.findViewById(R.id.facebookIdTV);
//        facebookToken = (TextView) facebookProfile.findViewById(R.id.facebookTokenTV);
//        facebookName  = (TextView) facebookProfile.findViewById(R.id.facebookNameTV);
//
//        facebookId.setText(facebook.getId());
//        facebookToken.setText(facebook.getToken());
//        facebookName.setText(facebook.getName());
//    }
//
//    //Init Twitter elements
//    private void initTwitter(TwitterModel twitter){
//        twitterProfile = findViewById(R.id.twitterLayoutInclude);
//
//        twitterId          = (TextView) twitterProfile.findViewById(R.id.twitterIdTV);
//        twitterToken       = (TextView) twitterProfile.findViewById(R.id.twitterTokenTV);
//        twitterDisplayName = (TextView) twitterProfile.findViewById(R.id.twitterDisplayNameTV);
//        twitterUsername    = (TextView) twitterProfile.findViewById(R.id.twitterUsernameTV);
//
//        twitterId.setText(twitter.getId());
//        twitterToken.setText(twitter.getToken());
//        twitterDisplayName.setText(twitter.getDisplayName());
//        twitterUsername.setText(twitter.getUsername());
//    }
//
//    //Init Google elements
//    private void initGoogle(GoogleModel google){
//        googleProfile = findViewById(R.id.googleLayoutInclude);
//
//        googleId    = (TextView) googleProfile.findViewById(R.id.googleIdTV);
//        googleToken = (TextView) googleProfile.findViewById(R.id.googleTokenTV);
//        googleEmail = (TextView) googleProfile.findViewById(R.id.googleEmailTV);
//        googleName  = (TextView) googleProfile.findViewById(R.id.googleNameTV);
//
//        googleId.setText(google.getId());
//        googleToken.setText(google.getToken());
//        googleEmail.setText(google.getEmail());
//        googleName.setText(google.getName());
//    }
//
//    //Init Vkontakte elements
//    private void initVkontakte(VkontakteModel vkontakte){
//        vkontakteProfile = findViewById(R.id.vkontakteLayoutInclude);
//
//        vkontakteId          = (TextView) vkontakteProfile.findViewById(R.id.vkontakteIdTV);
//        vkontakteToken       = (TextView) vkontakteProfile.findViewById(R.id.vkontakteTokenTV);
//        vkontakteUsername    = (TextView) vkontakteProfile.findViewById(R.id.vkontakteUsernameTV);
//        vkontakteDisplayName = (TextView) vkontakteProfile.findViewById(R.id.vkontakteDisplayNameTV);
//        vkontakteGender      = (TextView) vkontakteProfile.findViewById(R.id.vkontakteGenderTV);
//        vkontakteProfileURL  = (TextView) vkontakteProfile.findViewById(R.id.vkontakteProfileURLTV);
//
//        vkontakteId.setText(vkontakte.getId());
//        vkontakteToken.setText(vkontakte.getToken());
//        vkontakteUsername.setText(vkontakte.getUsername());
//        vkontakteDisplayName.setText(vkontakte.getDisplayName());
//        vkontakteGender.setText(vkontakte.getGender());
//        vkontakteProfileURL.setText(vkontakte.getProfileURL());
//    }


}
