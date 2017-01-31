package com.noname.poputchick.TestData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.noname.poputchick.R;

import java.util.ArrayList;


public class TestUsersAdapter extends ArrayAdapter<TestData> {

    public TestUsersAdapter(Context context, ArrayList<TestData> users) {
        super(context,0,users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TestData user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.test_user, parent, false);
        }

        TextView tvId = (TextView) convertView.findViewById(R.id.userId);
        TextView tvName = (TextView) convertView.findViewById(R.id.name);
        TextView tvEmail = (TextView) convertView.findViewById(R.id.email);
        TextView tvPhone = (TextView) convertView.findViewById(R.id.phone);

        tvId.setText(user.getId());
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        tvPhone.setText(user.getPhone());

        return convertView;
    }
}
