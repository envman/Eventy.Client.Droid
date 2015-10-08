package com.myleshumphreys.joinin.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.TimePicker;

import com.myleshumphreys.joinin.R;

import java.util.Calendar;

public class CreateEventActivity extends ActionBarActivity {

    private EditText editTextEventName;
    private EditText editTextEventDecription;
    private EditText editTextEventLocation;

    private Button buttonEventStartDate;
    private Button buttonEventStartTime;
    private Button buttonEventEndDate;
    private Button buttonEventEndTime;

    private String eventStartDate;
    private String eventStartTime;
    private String eventEndDate;
    private String eventEndTime;

    private Button buttonSelectImage;
    private Button buttonCreateEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        setupWidgets();
        //get shared preferences
    }

    private void setupWidgets()
    {
        editTextEventName = (EditText) findViewById(R.id.editTextEventName);
        editTextEventDecription = (EditText) findViewById(R.id.editTextEventDescription);
        editTextEventLocation = (EditText) findViewById(R.id.editTextEventName);
        buttonCreateEvent = (Button) findViewById(R.id.buttonCreateEvent);
    }

    public void eventStartDatePicker(View view) {
        DialogFragment eventStartDatePicker = new eventStartDatePickerFragment();
        eventStartDatePicker.show(getSupportFragmentManager(), "DatePicker");
    }

    public void eventStartDate(int year, int month, int day) {
        buttonEventStartDate = (Button)findViewById(R.id.datePickerEventStartDate);
        buttonEventStartDate.setText("Start Date : " + month + "/" + day + "/" + year);
        eventStartDate = (month + "/" + day + "/" + year);
    }

    public void eventStartTimePicker(View view) {
        DialogFragment eventStartTimePickerFragment = new eventStartTimePickerFragment();
        eventStartTimePickerFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    public void eventStartTime(int hour, int minute) {
        buttonEventStartTime = (Button)findViewById(R.id.timePickerEventStartTime);
        buttonEventStartTime.setText("Start Time : " + hour + ":" + minute);
        eventStartTime = (hour + ":" + minute);
    }

    public void eventEndDatePicker(View view) {
        DialogFragment eventEndDatePicker = new eventEndDatePickerFragment();
        eventEndDatePicker.show(getSupportFragmentManager(), "DatePicker");
    }

    public void eventEndDate(int year, int month, int day) {
        buttonEventEndDate = (Button)findViewById(R.id.datePickerEventEndDate);
        buttonEventEndDate.setText("End Date : " + month + "/" + day + "/" + year);
        eventEndDate = (month + "/" + day + "/" + year);
    }

    public void eventEndTimePicker(View view) {
        DialogFragment eventEndTimePickerFragment = new eventEndTimePickerFragment();
        eventEndTimePickerFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    public void eventEndTime(int hour, int minute) {
        buttonEventEndTime = (Button)findViewById(R.id.timePickerEventEndTime);
        buttonEventEndTime.setText("End Time : " + hour + ":" + minute);
        eventEndTime = (hour + ":" + minute);
    }

    public class eventStartDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            eventStartDate(yy, mm + 1, dd);
        }
    }

    public class eventStartTimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            eventStartTime(hourOfDay, minute);
        }
    }

    public class eventEndDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            eventEndDate(yy, mm + 1, dd);
        }
    }

    public class eventEndTimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            eventEndTime(hourOfDay, minute);
        }
    }

    private void endActivity() {
        CreateEventActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        endActivity();
    }
}