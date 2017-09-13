package com.example.hp1.myfinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DataBaseHomeWork extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Homework.db";
    public static final String TABLE_NAME = "Student_Table";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "SUBJECT";
    public static final String COL_3 = "DETAILS";
    public static final String COL_4 = "DAY";
    public static final String COL_5 = "MONTH";
    public static final String COL_6 ="YEAR";

    SQLiteDatabase db;

    public DataBaseHomeWork(Context context) {

        super(context, DATABASE_NAME, null, 5);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID TEXT ,SUBJECT TEXT,DETAILS TEXT,DAY TEXT,MONTH TEXT,YEAR TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void deleteData(String id)
    {
        db=this.getWritableDatabase();
        db.delete(TABLE_NAME,"ID=?",new String[]{id});;
    }

    public boolean insertDataToHomeWork(String id, String subject, String details, String day, String month, String year) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, subject);
        contentValues.put(COL_3, details);
        contentValues.put(COL_4, day);
        contentValues.put(COL_5, month);
        contentValues.put(COL_6, year);
        long result=db.insert(TABLE_NAME,null,contentValues);
        db.close();

        //To Check Whether Data is Inserted in DataBase
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

   public Cursor getAllDataFromHomeWork()
   {
       db=this.getWritableDatabase();
       return  db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
   }
    public void updateData(String id)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
    }
}