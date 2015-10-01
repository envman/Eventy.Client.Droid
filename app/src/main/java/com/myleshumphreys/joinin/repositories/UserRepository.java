package com.myleshumphreys.joinin.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.myleshumphreys.joinin.data.DatabaseVersion;
import com.myleshumphreys.joinin.models.User;

public class UserRepository {

    private static final String TABLE_USER = "user";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL_ADDRESS = "emailAddress";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_TOKEN = "token";

    public static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "( "
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_USERNAME + " TEXT "
            + COLUMN_EMAIL_ADDRESS + " TEXT, "
            + COLUMN_PASSWORD + " TEXT "
            + COLUMN_TOKEN + " TEXT "
            + ") ";

    private Context context = null;

    public UserRepository(Context context) {
        this.context = context;
    }

    public void create(User user) {
        SQLiteDatabase db = DatabaseVersion.getInstance(this.context).getWritableDatabase();
        ContentValues userValues = new ContentValues();
        userValues.put(COLUMN_USERNAME, user.getUserName());
        userValues.put(COLUMN_EMAIL_ADDRESS, user.getEmailAddress());
        userValues.put(COLUMN_PASSWORD, user.getPassword());
        userValues.put(COLUMN_TOKEN, user.getToken());
        db.insert(TABLE_USER, null, userValues);
        db.close();
    }

    public int update(User user) {
        SQLiteDatabase db = DatabaseVersion.getInstance(this.context).getWritableDatabase();
        ContentValues userValue = new ContentValues();
        userValue.put(COLUMN_USERNAME, user.getUserName());
        userValue.put(COLUMN_EMAIL_ADDRESS, user.getEmailAddress());
        userValue.put(COLUMN_PASSWORD, user.getPassword());
        userValue.put(COLUMN_TOKEN, user.getToken());
        int rowsAffected = db.update(TABLE_USER, userValue, COLUMN_ID + "=?", new String[]{String.valueOf(user.getId())});
        db.close();
        return rowsAffected;
    }
}
