package com.oromostudio.dovezu;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.oromostudio.dovezu.library.Constants;
import com.oromostudio.dovezu.models.RequestModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

                RequestModel request = new RequestModel();
                request.setStatus("Once");
                request.setStartPoint(startPoint.getText().toString());
                request.setEndPoint(endPoint.getText().toString());
                request.setTime(time.getText().toString());
                request.setWaitTime(Integer.toString((Integer) waitTime.getSelectedItem()));
                request.setFreeSeats(Integer.parseInt(freeSeats.getText().toString()));
                request.setMinPrice(Integer.parseInt(minPrice.getText().toString()));
                request.setMaxPrice(Integer.parseInt(maxPrice.getText().toString()));
                request.setComment(comment.getText().toString());
                request.setAccepted(false);



                finish();
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
