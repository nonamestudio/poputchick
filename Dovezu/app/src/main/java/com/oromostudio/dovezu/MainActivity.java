package com.oromostudio.dovezu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.oromostudio.dovezu.adapter.TabsPagerFragmentAdapter;
import com.oromostudio.dovezu.api.DovezuClient;
import com.oromostudio.dovezu.library.Constants;
import com.oromostudio.dovezu.models.ProfileModel;
import com.oromostudio.dovezu.models.RequestModel;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;
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

    TabsPagerFragmentAdapter adapter;

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
        initFab();
        initHeader();


    }

    private void initHeader() {
        Log.d("MAIN", "HEADER");
        View header = navigationView.getHeaderView(0);

        username = (TextView) header.findViewById(R.id.navigationHeaderUsername);
        email    = (TextView) header.findViewById(R.id.navigationHeaderEmail);
        phone    = (TextView) header.findViewById(R.id.navigationHeaderPhone);

        username.setTypeface(roboMed);
        email.setTypeface(roboReg);
        phone.setTypeface(roboReg);
    }

    @Override
    protected void onStart() {
        Log.d("MAIN", "START");
        super.onStart();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MAIN", "PAUSE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MAIN", "STOP");
    }

    private void checkUserData() {
        SharedPreferences pref = getSharedPreferences(Constants.USER_DATA, MODE_PRIVATE);
        String profile = pref.getString(Constants.USER_PROFILE, null);
        if(profile == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else{
            DovezuClient.checkAuth(getApplication(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if(statusCode == 776){
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else if(statusCode != 777){
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MAIN", "RESUME");
        checkUserData();
        getProfile();
//        SharedPreferences pref = getSharedPreferences(Constants.USER_DATA, MODE_PRIVATE);
//        Log.d("USER DATA", pref.getString(Constants.USER_PROFILE, "EMPTY"));

//        DovezuClient.checkAuth(getApplicationContext(), new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                Log.d("SUCCESS", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//                if(statusCode != 777){
//                    Log.d("SUCCESS", "77777777777777777777777777777777777777777777777");
//                    Toast.makeText(getApplicationContext(), "Login please", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                } else{
//                    Log.d("SUCCESS", "EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
//                    Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_SHORT).show();
//                    getProfile();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                Log.d("CHECK_AUTH", error.getMessage());
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });

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
        adapter = new TabsPagerFragmentAdapter(getApplicationContext(), getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        updateRequests();
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
        updateRequests();
        Log.d("ONCE TAB", Integer.toString(viewPager.getAdapter().getCount()));
    }

    private void showConstantlyTab() {
        viewPager.setCurrentItem(Constants.TAB_TWO);
    }

    private void showIntercityTab() {
        viewPager.setCurrentItem(Constants.TAB_THREE);
    }

    private void initFab() {
        fab = (FloatingActionButton) findViewById(R.id.floatingAddButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RequestActivity.class);
                intent.putExtra(Constants.EXTRA_REQUEST_TYPE, viewPager.getCurrentItem());
                startActivity(intent);
            }
        });
    }

    private void logout(){

        DovezuClient.logout(getApplicationContext(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_SHORT).show();

                DovezuClient.clearSession(MainActivity.this);

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("LOGOUT", error.getMessage());
                Toast.makeText(getApplicationContext(), getString(R.string.failure), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProfile(){

        Log.d("PROFILE", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        SharedPreferences pref = getSharedPreferences(Constants.USER_DATA, MODE_PRIVATE);
        if(pref.contains(Constants.USER_PROFILE)) {
            String jsonProfile = pref.getString(Constants.USER_PROFILE, "");
            Gson gson = new Gson();
            ProfileModel profile = gson.fromJson(jsonProfile, ProfileModel.class);
            Log.d("USERNAME", profile.getLocal().getUsername());
            username.setText(profile.getLocal().getUsername());
            email.setText(profile.getLocal().getEmail());
            phone.setText(profile.getLocal().getPhone());
        }else{
            username.setText("IBI");
            email.setText("IIB");
            phone.setText("ibib");
        }
    }

    private void updateRequests(){
        DovezuClient.getRequests(getApplicationContext(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<RequestModel>>(){}.getType();
                List<RequestModel> requests = gson.fromJson(response.toString(), listType);
                adapter.setData(requests);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Toast.makeText(getApplicationContext(), getString(R.string.failure), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
