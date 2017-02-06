package com.oromostudio.dovezu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.oromostudio.dovezu.api.DovezuApp;
import com.oromostudio.dovezu.models.ProfileModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        DovezuApp.getAPI().checkAuth().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().toString().compareTo(getString(R.string.successLogin)) == 0){
                    intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                } else{
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure to connect to server", Toast.LENGTH_SHORT).show();
                Log.d("Error", t.getMessage());
            }
        });

    }

}
