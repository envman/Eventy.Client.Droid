package com.myleshumphreys.joinin.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.ImageView;
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

    private ImageView imageViewImage;
    private Bitmap imageBitmap;

    private String eventStartDate;
    private String eventStartTime;
    private String eventEndDate;
    private String eventEndTime;

    private Button buttonSelectImage;
    private Button buttonCreateEvent;

    private int REQUEST_IMAGE_CAPTURE = 1;
    private int REQUEST_SELECT_FILE = 1;
    private int RESULT_LOAD_IMG = 1;
    private int REQUEST_FROM_GALLERY;
    private int REQUEST_FROM_CAMERA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        setupWidgets();
        selectImageButtonListener();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //get shared preferences
    }

    private void setupWidgets()
    {
        editTextEventName = (EditText) findViewById(R.id.editTextEventName);
        editTextEventDecription = (EditText) findViewById(R.id.editTextEventDescription);
        editTextEventLocation = (EditText) findViewById(R.id.editTextEventName);
        buttonSelectImage = (Button) findViewById(R.id.buttonSelectImage);
        buttonCreateEvent = (Button) findViewById(R.id.buttonCreateEvent);
        imageViewImage = (ImageView) findViewById(R.id.imageViewImage);
    }

    private void selectImageButtonListener()
    {
        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity.this);
                builder.setTitle("Location")
                        .setItems(R.array.select_image_dialog_array, new DialogInterface.OnClickListener() {
                                            public void onClick (DialogInterface dialog,int which){
                                                if (which == 0) {
                                                    REQUEST_FROM_CAMERA = 1;
                                                    dispatchTakePictureIntent();
                                                }
                                                if (which == 1) {
                                                    REQUEST_FROM_GALLERY = 1;
                                                    dispatchGalleryIntent();
                                                }
                                                if (which == 2) {
                                                    /// Add cancel
                                                }
                                            }
                                        }

                                );
                builder.show();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && REQUEST_FROM_CAMERA == 1) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageViewImage.setImageBitmap(imageBitmap);
            imageViewImage.setAdjustViewBounds(true);
        }
        else if (requestCode == REQUEST_SELECT_FILE && resultCode == RESULT_OK && REQUEST_FROM_GALLERY == 1)
        {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imageViewImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            imageViewImage.setAdjustViewBounds(true);
            setPic(picturePath);
        }
    }

    private void setPic(String picturePath) {
        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picturePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(picturePath, options);
        imageViewImage.setImageBitmap(bm);
    }

    private void dispatchGalleryIntent()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
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
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
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
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}