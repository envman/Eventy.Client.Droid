package com.myleshumphreys.joinin.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myleshumphreys.joinin.repositories.UserRepository;

public class DatabaseVersion extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "JoinInDB";

    private static DatabaseVersion Instance = null;

    public DatabaseVersion(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase _db) {
        _db.execSQL(UserRepository.CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
        //TODO
    }

    public static DatabaseVersion getInstance(Context context) {

        if (Instance == null) {
            Instance = new DatabaseVersion(context.getApplicationContext());
        }
        return Instance;
    }
}