package com.myleshumphreys.joinin.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.myleshumphreys.joinin.R;

public class EventActivity extends ActionBarActivity {

    public static final String ApplicationPreferences = "ApplicationPreferences";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        //Get token out of shared preferences
        //Setup retrofit for new auth headers
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_event, menu);
        return true;
    }

    private void logout() {
        ClearSharedPreferences();
        Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intentLogin);
        endActivity();
    }

    private void logoutDialog() {
        AlertDialog.Builder logoutAlert = new AlertDialog.Builder(this);
        logoutAlert.setTitle("Log Out");
        logoutAlert.setMessage("Are you sure you want to log out?");

        logoutAlert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                logout();
            }
        });

        logoutAlert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

        logoutAlert.show();
    }

    private void ClearSharedPreferences()
    {
        sharedPreferences = getSharedPreferences(ApplicationPreferences, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_event) {
            /// create event
            return true;
        }

        if (id == R.id.action_logout) {
            logoutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void endActivity() {
        EventActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        logoutDialog();
    }
}