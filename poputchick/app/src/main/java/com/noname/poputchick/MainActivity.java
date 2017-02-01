package com.noname.poputchick;

import android.app.Activity;
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
    private Button addUser;



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

//        for (int i = 0; i < 10; i++) {
//            TestData td = new TestData(Integer.toString(i),"Pasha","pavel@uee.com","+7777777");
//            usersList.add(td);
//        }
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

        addUser = (Button) findViewById(R.id.btn_add);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewUser();
            }
        });

    }

    private void addNewUser() {
        TestData td  = new TestData();

        TestApp.getApi().addUser(td).enqueue(new Callback<TestData>() {
            @Override
            public void onResponse(Call<TestData> call, Response<TestData> response) {

            }

            @Override
            public void onFailure(Call<TestData> call, Throwable t) {
                Toast.makeText(MainActivity.this, "An error occurred during connection to DB", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void connectToDB() {

//        try {
//            retrofit2.Response<List<TestData>> response = TestApp.getApi().getUsers().execute();
//        } catch (IOException e){
//            e.printStackTrace();
//        }



        TestApp.getApi().getUsers().enqueue(new Callback<List<TestData>>() {
            @Override
            public void onResponse(Call<List<TestData>> call, Response<List<TestData>> response) {
                usersList.addAll(response.body());
                usersArray.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TestData>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "An error occurred during connection to DB", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
