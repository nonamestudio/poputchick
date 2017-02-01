package com.noname.poputchick;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.noname.poputchick.TestData.TestData;
import com.noname.poputchick.TestData.TestInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddUserActivity extends AppCompatActivity {

    private static final String TAG = "AddUserActivity";
    private EditText nameET;
    private EditText emailET;
    private EditText phoneET;
    private boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        Gson gson = new Gson();
        Intent intent = getIntent();

        TestData user = gson.fromJson(intent.getStringExtra("user"), TestData.class);

        nameET = (EditText)findViewById(R.id.editText);
        emailET = (EditText)findViewById(R.id.editText2);
        phoneET = (EditText)findViewById(R.id.editText3);

        if (user != null){
            nameET.setText(user.getName());
            emailET.setText(user.getEmail());
            phoneET.setText(user.getPhone());
            isEdit = true;
        } else {
            nameET.setText("Name");
            emailET.setText("Email");
            phoneET.setText("Phone");
        }

    }



    public void addToDB(View view){

        String name = nameET.getText().toString();
        String email = emailET.getText().toString();
        String phone = phoneET.getText().toString();


        TestData td = new TestData();
        td.setName(name);
        td.setEmail(email);
        td.setPhone(phone);

        sendToServer(td);

    }

    private void sendToServer(TestData td) {

        if(isEdit){

            TestApp.getApi().updateUser(td).enqueue(new Callback<TestData>() {
                @Override
                public void onResponse(Call<TestData> call, Response<TestData> response) {
                    done();
                }

                @Override
                public void onFailure(Call<TestData> call, Throwable t) {
                    Toast.makeText(AddUserActivity.this, "Nooooo!", Toast.LENGTH_LONG).show();
                }
            });

        }else {
            TestApp.getApi().addUser(td).enqueue(new Callback<TestData>() {
                @Override
                public void onResponse(Call<TestData> call, Response<TestData> response) {
                    Toast.makeText(AddUserActivity.this, "Yeah!", Toast.LENGTH_LONG).show();
                    done();
                }

                @Override
                public void onFailure(Call<TestData> call, Throwable t) {
                    Toast.makeText(AddUserActivity.this, "Nooooo!", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    public void backToMain(View view){

        done();
    }

    private void done(){
        this.finish();
    }


}
