package com.example.hp1.myfinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dell on 9/2/2017.
 */

public class DataBaseVolunteerFarde extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Farde.db";
    public static final String TABLE_NAME = "Student_table";

    public static final String Col_1="ID";
    public static final String Col_2="PLACE";
    public static final String Col_3="ACTION";
    public static final String Col_4="HOURS";
    public static final String Col_5="DAY";
    public static final String Col_6="MONTH";
    public static final String Col_7="YEAR";
    public static final String Col_8="SIGNATURE";

    SQLiteDatabase db;

    public DataBaseVolunteerFarde(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID TEXT ,PLACE TEXT ,ACTION TEXT ,HOURS INTEGER ,DAY INTEGER ,MONTH INTEGER ,YEAR INTEGER ,SIGNATURE BLOB) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS "+ TABLE_NAME);
    }

    public boolean insertData(String _id, Rows volunteer, byte[] image)
    {
        db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Col_1,_id);
        contentValues.put(Col_2,volunteer.getPlace());
        contentValues.put(Col_3,volunteer.getAction());
        contentValues.put(Col_4,volunteer.getHours());
        contentValues.put(Col_5,volunteer.getDate().getDay());
        contentValues.put(Col_6,volunteer.getDate().getMonth());
        contentValues.put(Col_7,volunteer.getDate().getYear());
        contentValues.put(Col_8,image);
        long result=db.insert(TABLE_NAME,null,contentValues);
        db.close();
        return true;
    }

    public Cursor getAllData(){
        db=this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    }



}
