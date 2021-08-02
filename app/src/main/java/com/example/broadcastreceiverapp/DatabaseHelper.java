package com.example.broadcastreceiverapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String USERS_TABLE = "USERS_TABLE";
    public static final String ID = "ID";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_AGE = "user_age";
    public static final String USER_SEX = "user_sex";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "users.db", null, 1);
    }

    //This is called the first time a database is accessed. There should be code in here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement =
                "CREATE TABLE " + USERS_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_EMAIL + " VARCHAR, " + USER_PASSWORD + " VARCHAR, " + USER_AGE + " " +
                        "VARCHAR, " + USER_SEX + " VARCHAR)";

        db.execSQL(createTableStatement);
    }

    //this is called if the database version number changes. it prevents users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(CustomerModel customerModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues(); //Content values stores data in pairs ex. cv.put("name",value)

        cv.put(USER_EMAIL, customerModel.getCustomerEmail());
        cv.put(USER_PASSWORD, customerModel.getCustomerPassword());
        cv.put(USER_AGE,customerModel.getUser_age());
        cv.put(USER_SEX, customerModel.getUser_sex());

        long insert = db.insert(USERS_TABLE, null, cv);//the second parameter is if you try to insert an empty row, we keep it null
        if (insert == -1){
            return false;
        }
        else{
            return  true;
        }
    }

    public boolean checkUserEmail(String USER_EMAIL){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from USERS_TABLE where user_email = ?",new String[] {USER_EMAIL});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkUserNamePassword(String USER_EMAIL,String USER_PASSWORD){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from USERS_TABLE where user_email = ? and user_password= ?",new String[] {USER_EMAIL,USER_PASSWORD});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
}
