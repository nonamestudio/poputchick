package com.oromostudio.dovezu;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.oromostudio.dovezu.api.DovezuAPI;
import com.oromostudio.dovezu.api.DovezuClient;
import com.oromostudio.dovezu.library.Constants;
import com.oromostudio.dovezu.models.RequestModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class RequestActivity extends AppCompatActivity {

    private final int MINUTE_OFFSET = 10;

    private Button   create;
    private Button   cancel;
    private Spinner  waitTime;
    private TextView time;
    private EditText comment;
    private EditText minPrice;
    private EditText maxPrice;
    private EditText endPoint;
    private EditText freeSeats;
    private EditText startPoint;

    private int mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        initView();
        initButtons();
        initTimePickers();

    }

    private void initView() {
        create     = (Button)   findViewById(R.id.reqCreateCreateBtn);
        cancel     = (Button)   findViewById(R.id.reqCreateCancelBtn);
        waitTime   = (Spinner)  findViewById(R.id.reqCreateWaitTime);
        time       = (TextView) findViewById(R.id.reqCreateTime);
        startPoint = (EditText) findViewById(R.id.reqCreateStartPoint);
        endPoint   = (EditText) findViewById(R.id.reqCreateEndPoint);
        comment    = (EditText) findViewById(R.id.reqCreateComment);
        freeSeats  = (EditText) findViewById(R.id.reqCreateFreeSeats);
        minPrice   = (EditText) findViewById(R.id.reqCreateMinPrice);
        maxPrice   = (EditText) findViewById(R.id.reqCreateMaxPrice);
    }

    private void initButtons() {


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestParams params = new RequestParams();
                params.put(Constants.STATUS_FIELD,      "Once");
                params.put(Constants.START_POINT_FIELD, startPoint.getText().toString());
                params.put(Constants.END_POINT_FIELD,   endPoint.getText().toString());
                params.put(Constants.FREE_SEATS_FIELD,  Integer.parseInt(freeSeats.getText().toString()));
                params.put(Constants.TIME_FIELD,        time.getText().toString());
                params.put(Constants.WAIT_TIME_FIELD,   Integer.toString((Integer) waitTime.getSelectedItem()));
                params.put(Constants.MIN_PRICE_FIELD,   Integer.parseInt(minPrice.getText().toString()));
                params.put(Constants.MAX_PRICE_FIELD,   Integer.parseInt(maxPrice.getText().toString()));
                params.put(Constants.COMMENT_FIELD,     comment.getText().toString());
                params.put(Constants.ACCEPTED_FIELD,    false);



                DovezuClient.addRequest(getApplicationContext(), params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getApplicationContext(), getString(R.string.failure), Toast.LENGTH_SHORT).show();
                        Log.d("REQUEST", error.getMessage());
                    }
                });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE) + MINUTE_OFFSET;

                TimePickerDialog timePicker = new TimePickerDialog(RequestActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);

                timePicker.show();

            }
        });
    }

    private void initTimePickers() {
        Date date = new Date();
        SimpleDateFormat tf = new SimpleDateFormat("hh:mm");
        date.setTime(date.getTime() + (MINUTE_OFFSET * Constants.MINUTE_IN_MILLIS));
        time.setText(tf.format(date));

        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(10);
        list.add(15);
        list.add(20);

        ArrayAdapter<Integer> adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
        waitTime.setAdapter(adapter);
    }
}
