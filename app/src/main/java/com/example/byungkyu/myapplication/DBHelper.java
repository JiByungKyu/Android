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
        db.execSQL("create table if not exists positive_response_tb(_id integer primary key autoincrement,ID byte, CONTENT text);");
        db.execSQL("create table if not exists negative_response_tb(_id integer primary key autoincrement,ID byte, CONTENT text);");
        db.execSQL("create table if not exists local_data_group_tb(_id integer primary key autoincrement,ID byte, CONTENT text);");
        //아날로그 테이블은 check 함수를 적용하여 아니면 exception 이 생기게 해야한다.
        db.execSQL("create table if not exists analog_tb(_id integer primary key autoincrement,ID byte, CONTENT text, UNITVALUE number, UNIT text);");
        //연료 사용정보 역시 check 함수를 사용해야할 것 같다.
        db.execSQL("create table if not exists fuel_use_tb(_id integer primary key autoincrement,ID byte, CONTENT text, UNITVALUE number, UNIT text);");
        //가동시간 사용정보 역시 check 함수를 사용해야할 것 같다.
        db.execSQL("create table if not exists operation_time_tb(_id integer primary key autoincrement,ID byte, CONTENT text, UNITVALUE number, UNIT text);");
        //필터오일 사용정보 역시 check 함수를 사용해야할 것 같다.
        db.execSQL("create table if not exists filter_oil_use_tb(_id integer primary key autoincrement,ID byte, CONTENT text, UNITVALUE number, UNIT text);");
        //필터오일 교환 주기정보 역시 check 함수를 사용해야할 것 같다.
        db.execSQL("create table if not exists filter_change_period_tb(_id integer primary key autoincrement,ID byte, CONTENT text, UNITVALUE number, UNIT text);");
        //오류 고장 정보
        db.execSQL("create table if not exists fault_code_list_tb(_id integer primary key autoincrement,ID text, CONTENT_KR text, CONTENT_ER text, INDEX byte, FMI byte);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
