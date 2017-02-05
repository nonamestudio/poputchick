package com.oromostudio.dovezu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.oromostudio.dovezu.api.DovezuApp;
import com.oromostudio.dovezu.models.LoginModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;

    /////////////////////////////
    //Login scope
    /////////////////////////////
    private View loginView;
    private EditText loginEmail;
    private EditText loginPassword;
    private TextView loginSignup;
    private Button loginLogin;
    private Button loginFacebook;
    private Button loginTwitter;
    private Button loginGoogle;
    private Button loginVkontakte;
    //////////////////////////////

    ////////////////////////////
    //Register scope
    ////////////////////////////
    private View registerView;
    private EditText registerUsername;
    private EditText registerEmail;
    private EditText registerPhone;
    private EditText registerPassword;
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

                if(email.length() > 0){
                    LoginModel login = new LoginModel();
                    login.setEmail(email);
                    login.setPassword(password);

                    DovezuApp.getAPI().loginLocal(login).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.body().toString().compareTo(getString(R.string.successLogin)) == 0){
                                toProfile();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }
        });

        //Facebook button
        loginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DovezuApp.getAPI().loginFacebook().enqueue(new Callback<String>() {
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
                DovezuApp.getAPI().loginTwitter().enqueue(new Callback<String>() {
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
                DovezuApp.getAPI().loginGoogle().enqueue(new Callback<String>() {
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
                DovezuApp.getAPI().loginVkontakte().enqueue(new Callback<String>() {
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
        loginLogin     = (Button) loginView.findViewById(R.id.loginBtn);
        loginFacebook  = (Button) loginView.findViewById(R.id.facebookBtn);
        loginTwitter   = (Button) loginView.findViewById(R.id.twitterBtn);
        loginGoogle    = (Button) loginView.findViewById(R.id.googleBtn);
        loginVkontakte = (Button) loginView.findViewById(R.id.vkontakteBtn);


        registerView = findViewById(R.id.registerLayout);

        registerUsername = (EditText) registerView.findViewById(R.id.registerUsernameET);
        registerEmail    = (EditText) registerView.findViewById(R.id.registerEmailET);
        registerPhone    = (EditText) registerView.findViewById(R.id.registerPhoneET);
        registerPassword = (EditText) registerView.findViewById(R.id.registerPasswordET);

    }

    public void onSignupPage(View view){

        viewFlipper.setDisplayedChild(1);

    }
}
