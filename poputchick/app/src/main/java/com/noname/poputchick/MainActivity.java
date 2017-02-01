package com.noname.poputchick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.noname.poputchick.TestData.TestData;
import com.noname.poputchick.TestData.TestUsersAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    //delete this. Only for testing
    private ListView users;
    private TestUsersAdapter usersArray;
    private ArrayList<TestData> usersList;
    private Button getUsersBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //delete this. Only for testing
        initTest();

    }

    //delete this. Only for testing
    private void initTest() {
        usersList = new ArrayList<>();

        usersArray = new TestUsersAdapter(this, usersList);

        users = (ListView) findViewById(R.id.listView);
        users.setAdapter(usersArray);

        getUsersBtn = (Button) findViewById(R.id.btn_allusers);
        getUsersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToDB();
            }
        });



    }

    public void addNewUser(View view) {

        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);

    }

    public void editUser(View view){
        Intent intent = new Intent(this, AddUserActivity.class);

        startActivity(intent);
    }

    private void connectToDB() {
        usersList.clear();
        usersArray.notifyDataSetChanged();

        TestApp.getApi().getUsers().enqueue(new Callback<List<TestData>>() {
            @Override
            public void onResponse(Call<List<TestData>> call, Response<List<TestData>> response) {
                if(response.body().size() > 0) {

                    usersList.addAll(response.body());
                    usersArray.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<TestData>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "An error occurred during connection to DB", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void conn(){
        connectToDB();
    }
}
