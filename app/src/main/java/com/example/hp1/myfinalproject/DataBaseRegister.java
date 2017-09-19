package com.example.hp1.myfinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TabLayout;


public class DataBaseRegister extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Student.db";
    private static final String TABLE_NAME = "Student_table";

    private static final String COl_1="ID";
    private static final String COL_2="FIRST_NAME";
    private static final String COL_3="LAST_NAME";
    private static final String COL_4="EMAIL";
    private static final String COL_5="PASSWORD";
    private static final String COL_6="SPECIALTY";
    private static final String COl_7="ENGPOINTS";
    private static final String COl_8="MATHPOINTS";
    private static final String COL_9="GRADE";
    private static final String COL_10="PROF_PIC";


    public DataBaseRegister(Context context)
    {
        super(context, DATABASE_NAME, null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER ,FIRST_NAME TEXT ,LAST_NAME TEXT ,EMAIL TEXT ,PASSWORD TEXT ,SPECIALTY TEXT ,ENGPOINTS INTEGER ,MATHPOINTS INTEGER ,GRADE INTEGER ,PROF_PIC BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return  db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    }

    public boolean insertData(String id, String firt_name, String last_name, String Email, String password, String specialty, int engpoints, int mathpoints, int grade ,byte[] image)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COl_1, id);
        contentValues.put(COL_2,firt_name);
        contentValues.put(COL_3,last_name);
        contentValues.put(COL_4,Email);
        contentValues.put(COL_5,password);
        contentValues.put(COL_6,specialty);
        contentValues.put(COl_7,engpoints+"");
        contentValues.put(COl_8,mathpoints+"");
        contentValues.put(COL_9,grade+"");
        contentValues.put(COL_10,image);
        long result=db.insert(TABLE_NAME,null,contentValues);
        db.close();
        //To Check Whether Data is Inserted in DataBase
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

        public int search_nomatch(String id, String Email, String password)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("Select * from " + TABLE_NAME,null);
            if(res!=null&&res.getCount()>0)
                while (res.moveToNext()){
                    if(id.equals(res.getString(0)))
                        return 1;
                    else
                        if(Email.equals(res.getString(3)))
                            return 2;
                    else
                        if(password.equals(res.getString(4)))
                            return 3;
                }
            return 0;
        }

        public int check_login(String Email,String password)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("Select * from " + TABLE_NAME,null);
            if(res!=null&&res.getCount()>0)
                while (res.moveToNext()){
                   if(Email.equals(res.getString(3))&&password.equals(res.getString(4)))
                       return 0;
                }
            return -1;
        }

        public void updateRegisterData(String id, String firt_name, String last_name, String Email, String password, String specialty, int engpoints, int mathpoints, int grade ,byte[] image)
        {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
           contentValues.put(COl_1, id);
            contentValues.put(COL_2,firt_name);
            contentValues.put(COL_3,last_name);
            contentValues.put(COL_4,Email);
            contentValues.put(COL_5,password);
            contentValues.put(COL_6,specialty);
            contentValues.put(COl_7,engpoints+"");
            contentValues.put(COl_8,mathpoints+"");
            contentValues.put(COL_9,grade+"");
            contentValues.put(COL_10,image);
            db.update(TABLE_NAME,contentValues,"EMAIL=?",new String[]{Email});
        }


    }



