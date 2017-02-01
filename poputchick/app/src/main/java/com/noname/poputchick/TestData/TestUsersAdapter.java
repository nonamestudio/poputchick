package com.noname.poputchick.TestData;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.noname.poputchick.AddUserActivity;
import com.noname.poputchick.MainActivity;
import com.noname.poputchick.R;
import com.noname.poputchick.TestApp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TestUsersAdapter extends ArrayAdapter<TestData> {

    private Context context;

    public final static String EXTRA_USER = "user";

    public TestUsersAdapter(Context context, ArrayList<TestData> users) {
        super(context,0,users);

        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TestData user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.test_user, parent, false);
        }

        Button edit = (Button) convertView.findViewById(R.id.edit);
        Button remove = (Button) convertView.findViewById(R.id.remove);

        edit.setTag(position);
        remove.setTag(position);

        remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int position = (Integer) view.getTag();

                TestData user = getItem(position);

                TestApp.getApi().removeUser(user).enqueue(new Callback<TestData>() {
                    @Override
                    public void onResponse(Call<TestData> call, Response<TestData> response) {
                        ((MainActivity)context).conn();
                    }

                    @Override
                    public void onFailure(Call<TestData> call, Throwable t) {
                        Toast.makeText(context, "Nooooo!", Toast.LENGTH_LONG).show();
                    }
                });
            }

        });

        edit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                int position = (Integer) view.getTag();

                TestData user = getItem(position);

                Gson gson = new Gson();
                String json = gson.toJson(user);

                Intent intent = new Intent(context, AddUserActivity.class);
                intent.putExtra(EXTRA_USER, json);
                intent.putExtra("type","edit");
                context.startActivity(intent);
            }
        });

        TextView tvName = (TextView) convertView.findViewById(R.id.name);
        TextView tvEmail = (TextView) convertView.findViewById(R.id.email);
        TextView tvPhone = (TextView) convertView.findViewById(R.id.phone);

        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        tvPhone.setText(user.getPhone());

        return convertView;
    }
}
