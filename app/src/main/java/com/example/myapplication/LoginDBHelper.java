package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LoginDBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "UserInformation.db";

    public LoginDBHelper(Context context) {
        super(context, "UserInformation.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase UserDB) {

        UserDB.execSQL("create Table users(username TEXT primary Key, password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase UserDB, int i, int i1) {

        UserDB.execSQL("drop table if exists users");

    }

    public Boolean insertData(String username, String email, String password){

        SQLiteDatabase UserDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("e-mail", email);
        contentValues.put("password", password);
        long results = UserDB.insert("users", null, contentValues);
        if(results==-1) return false;
        else
            return true;

    }

    public boolean checkusername(String username){

        SQLiteDatabase UserDB = this.getWritableDatabase();
        Cursor cursor = UserDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;

    }

    public boolean checkusernamepassword(String username, String password){

        SQLiteDatabase UserDB = this.getWritableDatabase();
        Cursor cursor = UserDB.rawQuery("Select * from users where username = ? and password = ?", new String[]{username, password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
}
