package com.oromostudio.dovezu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.oromostudio.dovezu.api.DovezuApp_old;
import com.oromostudio.dovezu.api.DovezuClient;
import com.oromostudio.dovezu.library.Constants;
import com.oromostudio.dovezu.models.ProfileModel;
import com.oromostudio.dovezu.models.SignUpModel;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.cookie.MalformedCookieException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;

    /////////////////////////////
    //Login scope
    /////////////////////////////
    private View     loginView;
    private EditText loginEmail;
    private EditText loginPassword;
    private TextView loginSignup;
    private Button   loginLogin;
    //////////////////////////////

    ////////////////////////////
    //Register scope
    ////////////////////////////
    private View     registerView;
    private EditText registerUsername;
    private EditText registerEmail;
    private EditText registerPhone;
    private EditText registerPassword;
    private Button   registerBtn;
    /////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        btnListners();

    }

    private void btnListners() {


        //Login button
        loginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                //TODO: Validate email

                if(email.length() > 0){
                    if(password.length() > 0){
                        RequestParams params = new RequestParams();
                        params.put(Constants.EMAIL_FIELD, email);
                        params.put(Constants.PASSWORD_FIELD, password);

                        final PersistentCookieStore cookieStore = new PersistentCookieStore(getApplicationContext());

                        DovezuClient.login(getApplicationContext(), cookieStore, params, new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                Gson gson = new Gson();
                                ProfileModel profile = gson.fromJson(response.toString(), ProfileModel.class);

                                Log.d("LOGIN", response.toString());


                                SharedPreferences pref = getSharedPreferences(Constants.USER_DATA, MODE_PRIVATE);
                                SharedPreferences.Editor edit = pref.edit();
                                edit.putString(Constants.USER_ID, profile.getId());
                                edit.putString(Constants.USER_PROFILE, response.toString());
                                edit.apply();

                                SharedPreferences cookiePref = getSharedPreferences(Constants.SAVE_COOKIE, MODE_PRIVATE);
                                SharedPreferences.Editor cookEd = cookiePref.edit();
                                List<Cookie> cookies = cookieStore.getCookies();
                                for (Cookie cookie : cookies) {
                                    if (cookie.getName().compareTo(Constants.COOKIE_NAME) == 0) {
                                        if (cookie.getDomain().compareTo(Constants.DOMAIN) == 0) {
                                            cookEd.putString(Constants.SESSION, cookie.getValue());
                                            cookEd.apply();
                                            break;
                                        }
                                    }
                                }
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                Toast.makeText(getApplicationContext(), getString(R.string.failure), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.noPassword), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.noEmail), Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username = registerUsername.getText().toString();
                String email    = registerEmail   .getText().toString();
                String phone    = registerPhone   .getText().toString();
                String password = registerPassword.getText().toString();

                Boolean passed = true;

                if(username.length() == 0) {Toast.makeText(LoginActivity.this, getString(R.string.noUsername),     Toast.LENGTH_SHORT).show(); passed = false;}
                if(email.length()    == 0) {Toast.makeText(LoginActivity.this, getString(R.string.noEmail),        Toast.LENGTH_SHORT).show(); passed = false;}
                if(phone.length()    == 0) {Toast.makeText(LoginActivity.this, getString(R.string.noPhone), Toast.LENGTH_SHORT).show(); passed = false;}
                if(password.length() == 0) {Toast.makeText(LoginActivity.this, getString(R.string.noPassword),     Toast.LENGTH_SHORT).show(); passed = false;}

                //TODO: Validate email and phone number

                if(passed) {
                    SignUpModel signUpModel = new SignUpModel();

                    signUpModel.setUsername(username);
                    signUpModel.setEmail   (email);
                    signUpModel.setPhone   (phone);
                    signUpModel.setPassword(password);

                    RequestParams params = new RequestParams();

                    params.put(Constants.USERNAME_FIELD, username);
                    params.put(Constants.EMAIL_FIELD,    email);
                    params.put(Constants.PHONE_FIELD,    phone);
                    params.put(Constants.PASSWORD_FIELD, password);

                    DovezuClient.signup(getApplicationContext(), params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            viewFlipper.setDisplayedChild(0);
                            Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(getApplicationContext(), getString(R.string.failure), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }

    private void toProfile() {
        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void init() {

        viewFlipper = (ViewFlipper) findViewById(R.id.activity_login);

        loginView = findViewById(R.id.loginLayout);

        loginEmail     = (EditText) loginView.findViewById(R.id.loginEmailET);
        loginPassword  = (EditText) loginView.findViewById(R.id.loginPasswordET);
        loginSignup    = (TextView) loginView.findViewById(R.id.signupLink);
        loginLogin     = (Button)   loginView.findViewById(R.id.loginBtn);

        registerView = findViewById(R.id.registerLayout);

        registerUsername = (EditText) registerView.findViewById(R.id.registerUsernameET);
        registerEmail    = (EditText) registerView.findViewById(R.id.registerEmailET);
        registerPhone    = (EditText) registerView.findViewById(R.id.registerPhoneET);
        registerPassword = (EditText) registerView.findViewById(R.id.registerPasswordET);
        registerBtn      = (Button)   registerView.findViewById(R.id.registerBtn);

    }

    public void onSignupPage(View view){

        viewFlipper.setDisplayedChild(1);

    }
}
