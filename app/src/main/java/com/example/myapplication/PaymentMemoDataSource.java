package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PaymentMemoDataSource {

    private static final String LOG_TAG = PaymentMemoDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private PaymentMemoDbHelper dbHelper;

    public PaymentMemoDataSource(Context context) {
        Log.d(LOG_TAG, "The DataSource creates the dbHelper.");
        dbHelper = new PaymentMemoDbHelper(context);
    }
}
