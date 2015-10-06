package com.myleshumphreys.joinin.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.myleshumphreys.joinin.Handlers.DatePickerFragmentHandler;
import com.myleshumphreys.joinin.Handlers.TimePickerFragmentHandler;
import com.myleshumphreys.joinin.R;

public class CreateEventActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        //setup widgets
        //get shared preferences
    }

    public void GetDatePickerDialog(View v) {
        DatePickerFragmentHandler newFragment = new DatePickerFragmentHandler();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void GetTimePickerDialog(View v) {
        TimePickerFragmentHandler newFragment = new TimePickerFragmentHandler();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    private void endActivity() {
        CreateEventActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        endActivity();
    }
}