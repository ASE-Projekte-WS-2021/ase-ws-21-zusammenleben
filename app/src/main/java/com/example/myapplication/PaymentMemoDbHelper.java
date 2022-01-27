package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PaymentMemoDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = PaymentMemoDbHelper.class.getSimpleName();

    public static final String DB_NAME = "payment_list.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_PAYMENT_LIST = "payment_list";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCT = "product";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_COST = "cost";

    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_PAYMENT_LIST +
            "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCT + " TEXT NOT NULL, " +
                COLUMN_QUANTITY + " INTEGER NOT NULL, " +
                COLUMN_COST + " DOUBLE NOT NULL);";



    public PaymentMemoDbHelper(Context context) {
        /*super(context, "PLACEHOLDER_DATABASENAME", null, 1); was just for testing purpose*/
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper has successfully created the Database: " + getDatabaseName() + "!");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            Log.d(LOG_TAG, "The Table will be created through the SQL-statement: " + SQL_CREATE + " angelegt.");
            sqLiteDatabase.execSQL(SQL_CREATE);
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Error while creating the table: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

