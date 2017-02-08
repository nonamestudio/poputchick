package com.oromostudio.dovezu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.oromostudio.dovezu.api.DovezuAPI;

public class MainActivity extends AppCompatActivity {

    private final String FAILURE = "id not contains";

    private Toolbar toolbar;

    private Intent intent;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
    }


    @Override
    protected void onStart() {
        super.onStart();

        sharedPreferences = getSharedPreferences(DovezuAPI.getSaveCookie(), MODE_PRIVATE);

        if(!sharedPreferences.contains(DovezuAPI.getSaveCookie())){
            intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        toolbar.inflateMenu(R.menu.menu);
    }
}
