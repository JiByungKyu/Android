package com.example.byungkyu.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by YJ on 2017-08-04.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAMME = "gusto.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) { super(context,DATABASE_NAMME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
