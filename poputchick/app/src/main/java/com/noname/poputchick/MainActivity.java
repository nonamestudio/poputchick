package com.noname.poputchick;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.noname.poputchick.TestData.TestData;
import com.noname.poputchick.TestData.TestUsersAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    //delete this. Only for testing
    private ListView users;
    private TestUsersAdapter usersArray;
    private ArrayList<TestData> usersList;



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

        for (int i = 0; i < 10; i++) {
            TestData td = new TestData(Integer.toString(i),"Pasha","pavel@uee.com","+7777777");
            usersList.add(td);
        }
        usersArray = new TestUsersAdapter(this, usersList);

        users = (ListView) findViewById(R.id.listView);
        users.setAdapter(usersArray);

    }
}
