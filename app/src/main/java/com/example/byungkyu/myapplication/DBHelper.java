package com.example.byungkyu.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by YJ on 2017-08-04.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "gusto.db";
    private static final int DATABASE_VERSION = 1;
    public static HashMap<String, String[]> map;
    private String[] info;

    public DBHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        map = new HashMap<String, String[]>();
        info = new String[3];
    }

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
        //db.execSQL("create table if not exists fault_code_list_tb(_id integer primary key autoincrement,ID text, CONTENT_KR text, CONTENT_ER text, FCL_INDEX byte, FMI byte);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exits fault_code_list_tb");
        System.out.println("삭제");
        onCreate(db);
    }

    public void selectCurrentErrorInfo(SQLiteDatabase db){
        Cursor cursor;
        cursor  = db.rawQuery("select * from fault_code_list_tb;",null);
        System.out.println(cursor);
        while(cursor.moveToNext()){
            String ID = cursor.getString(1);
            String CONTENT_KR = cursor.getString(2);
            String CONTENT_ER = cursor.getString(3);
            int FCL_INDEX = cursor.getInt(4);
            int FMI = cursor.getInt(5);

            info[0] = ID;
            info[1] = CONTENT_KR;
            info[2] = CONTENT_ER;

            System.out.println("ID :" + ID + ",KR : " + CONTENT_KR + ",ER : " +CONTENT_ER + ",INDEX : " +FCL_INDEX
                    + "FMI : " + FMI);
            map.put(FCL_INDEX+""+FMI,info);
        }
    }
}