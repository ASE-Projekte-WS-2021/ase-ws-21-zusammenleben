package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PaymentMemoDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = PaymentMemoDbHelper.class.getSimpleName();

    public PaymentMemoDbHelper(Context context) {
        super(context, "PLACEHOLDER_DATABASENAME", null, 1);
        Log.d(LOG_TAG, "DbHelper has successfully created the Database: " + getDatabaseName() + "!");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

