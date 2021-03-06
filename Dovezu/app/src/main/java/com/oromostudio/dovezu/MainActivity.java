package com.oromostudio.dovezu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.oromostudio.dovezu.adapter.TabsPagerFragmentAdapter;
import com.oromostudio.dovezu.api.DovezuAPI;
import com.oromostudio.dovezu.api.DovezuClient;
import com.oromostudio.dovezu.library.Constants;
import com.oromostudio.dovezu.models.ProfileModel;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private final String FAILURE = "id not contains";

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private NavigationView navigationView;

    //----------------------------
    //Navigation view header
    private TextView username;
    private TextView email;
    private TextView phone;
    //------------------------------


    //-----------------------------
    //FONTS
    private Typeface roboMed;
    private Typeface roboReg;
    //-----------------------------------------------------------


    private Intent intent;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roboMed = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
        roboReg = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");

        initToolbar();
        initTabs();
        initNavigationView();
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

    @Override
    protected void onResume() {
        super.onResume();
        View header = navigationView.getHeaderView(0);

        username = (TextView) header.findViewById(R.id.navigationHeaderUsername);
        email    = (TextView) header.findViewById(R.id.navigationHeaderEmail);
        phone    = (TextView) header.findViewById(R.id.navigationHeaderPhone);

        username.setTypeface(roboMed);
        email.setTypeface(roboReg);
        phone.setTypeface(roboReg);

        getProfile();
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

    private void initTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(getApplicationContext(), getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navigation);



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                switch (item.getItemId()){
                    case R.id.navigationOnce:
                        showOnceTab();
                        break;
                    case R.id.navigationConstantly:
                        showConstantlyTab();
                        break;
                    case R.id.navigationIntercity:
                        showIntercityTab();
                        break;
                    case R.id.navigationLogout:
                        logout();
                        break;
                }
                return true;
            }
        });
    }

    private void showOnceTab() {
        viewPager.setCurrentItem(Constants.TAB_ONE);
    }

    private void showConstantlyTab() {
        viewPager.setCurrentItem(Constants.TAB_TWO);
    }

    private void showIntercityTab() {
        viewPager.setCurrentItem(Constants.TAB_THREE);
    }

    private void logout(){

        DovezuClient.logout(getApplicationContext(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), getString(R.string.failure), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProfile(){

        DovezuClient.profile(getApplicationContext(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new Gson();
                ProfileModel profile = gson.fromJson(response.toString(), ProfileModel.class);
                username.setText(profile.getLocal().getUsername());
                email.setText(profile.getLocal().getEmail());
                phone.setText(profile.getLocal().getPhone());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getApplicationContext(), getString(R.string.failureProfile), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
