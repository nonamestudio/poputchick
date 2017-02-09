package com.oromostudio.dovezu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.oromostudio.dovezu.api.DovezuAPI;
import com.oromostudio.dovezu.api.DovezuApp_old;
import com.oromostudio.dovezu.api.DovezuClient;
import com.oromostudio.dovezu.models.LocalModel;
import com.oromostudio.dovezu.models.LoginModel;
import com.oromostudio.dovezu.models.ProfileModel;
import com.oromostudio.dovezu.models.SignUpModel;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.cookie.Cookie;
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

    /////////////////////////////
    //Register method choose
    /////////////////////////////
    private View   chooseView;
    private Button registerLocal;
    private Button loginFacebook;
    private Button loginTwitter;
    private Button loginGoogle;
    private Button loginVkontakte;
    /////////////////////////////

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

    private SharedPreferences sharedPreferences;
    private AsyncHttpClient client;
    private RequestParams params;
    private PersistentCookieStore cookieStore;

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
                        LoginModel login = new LoginModel();
                        login.setEmail(email);
                        login.setPassword(password);

                        client = new AsyncHttpClient();
                        cookieStore = new PersistentCookieStore(getApplicationContext());
                        client.setCookieStore(cookieStore);
                        params = new RequestParams();
                        params.put(DovezuAPI.getEmailField(), email);
                        params.put(DovezuAPI.getPasswordField(), password);
                        cookieStore.clear();

                        DovezuClient.login(getApplicationContext(), params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Toast.makeText(getApplicationContext(), getString(R.string.failure), Toast.LENGTH_SHORT).show();
                            }
                        });

//                        client.post(DovezuAPI.getLogin(), params, new AsyncHttpResponseHandler() {
//                            @Override
//                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                                sharedPreferences = getSharedPreferences(DovezuAPI.getSaveCookie(),MODE_PRIVATE);
//                                SharedPreferences.Editor ed = sharedPreferences.edit();
//                                List<Cookie> cookies = cookieStore.getCookies();
//                                for (Cookie cookie : cookies){
//                                    if(cookie.getName().compareTo(DovezuAPI.getCookieName()) == 0){
//                                        if(cookie.getDomain().compareTo(DovezuAPI.getDomain()) == 0) {
//                                            ed.putString(DovezuAPI.getSaveCookie(), cookie.getValue());
//                                            ed.commit();
//                                            break;
//                                        }
//                                    }
//                                }
//                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                startActivity(intent);
//                            }
//
//                            @Override
//                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                                Toast.makeText(getApplicationContext(), getString(R.string.failure), Toast.LENGTH_SHORT).show();
//                            }
//                        });

                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.noPassword), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.noEmail), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //LocalRegister button
        registerLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setDisplayedChild(2);
            }
        });

        //Facebook button
        loginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DovezuApp_old.getAPI().loginFacebook().enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body().toString().compareTo(getString(R.string.successFacebook)) == 0){
                            toProfile();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });

        //Twitter button
        loginTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DovezuApp_old.getAPI().loginTwitter().enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body().toString().compareTo(getString(R.string.successTwitter)) == 0){
                            toProfile();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });

        //Google button
        loginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DovezuApp_old.getAPI().loginGoogle().enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body().toString().compareTo(getString(R.string.successGoogle)) == 0){
                            toProfile();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });

        //Vkontakte button
        loginVkontakte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DovezuApp_old.getAPI().loginVkontakte().enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body().toString().compareTo(getString(R.string.successVkontakte)) == 0){
                            toProfile();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username = registerUsername.getText().toString();
                String email    = registerEmail.getText().toString();
                String phone    = registerPhone.getText().toString();
                String password = registerPassword.getText().toString();

                Boolean passed = true;

                if(username.length() == 0) {Toast.makeText(LoginActivity.this, getString(R.string.noUsername), Toast.LENGTH_SHORT).show(); passed = false;}
                if(email.length()    == 0) {Toast.makeText(LoginActivity.this, getString(R.string.noEmail),    Toast.LENGTH_SHORT).show(); passed = false;}
                if(phone.length()    == 0) {Toast.makeText(LoginActivity.this, getString(R.string.noPhone),    Toast.LENGTH_SHORT).show(); passed = false;}
                if(password.length() == 0) {Toast.makeText(LoginActivity.this, getString(R.string.noPassword), Toast.LENGTH_SHORT).show(); passed = false;}

                //TODO: Validate email and phone number

                if(passed) {
                    SignUpModel signUpModel = new SignUpModel();

                    signUpModel.setUsername(username);
                    signUpModel.setEmail(email);
                    signUpModel.setPhone(phone);
                    signUpModel.setPassword(password);

                    client = new AsyncHttpClient();
                    cookieStore = new PersistentCookieStore(getApplicationContext());
                    client.setCookieStore(cookieStore);
                    params = new RequestParams();

                    params.put(DovezuAPI.getUsernameField(), username);
                    params.put(DovezuAPI.getEmailField(), email);
                    params.put(DovezuAPI.getPhoneField(), phone);
                    params.put(DovezuAPI.getPasswordField(), password);

                    Log.d("SIGNUP", DovezuAPI.getSignup());


                    client.post(DovezuAPI.getSignup(), params, new AsyncHttpResponseHandler() {
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


        chooseView = findViewById(R.id.chooseLayout);

        registerLocal  = (Button) chooseView.findViewById(R.id.localBtn);
        loginFacebook  = (Button) chooseView.findViewById(R.id.facebookBtn);
        loginTwitter   = (Button) chooseView.findViewById(R.id.twitterBtn);
        loginGoogle    = (Button) chooseView.findViewById(R.id.googleBtn);
        loginVkontakte = (Button) chooseView.findViewById(R.id.vkontakteBtn);


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
