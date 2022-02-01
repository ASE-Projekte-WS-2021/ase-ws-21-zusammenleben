package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

 /* this class is the Datasource and keeps the connection to the database.
    A ref to the database object can be requested and the creation process of
    the table can be started...
 */


public class PaymentMemoDataSource extends SQLiteOpenHelper{

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "PaymentManager";
    private static final String TABLE_NAME = "Payments";

    private static final String KEY_ID = "id";
    private static final String KEY_COST = "cost";
    private static final String KEY_PRODUCT = "product";

    public PaymentMemoDataSource(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PAYMENTS =
                "CREATE TABLE " + TABLE_NAME +"("+KEY_ID+" INTEGER PRIMARY KEY,"+KEY_COST+" TEXT,"
                        +KEY_PRODUCT+" TEXT"+")";
        db.execSQL(CREATE_TABLE_PAYMENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void addPayment(PaymentMemo p) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COST, p.getCost());
        values.put(KEY_PRODUCT, p.getProduct());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }
}

 /* this class is the Datasource and keeps the connection to the database.
    A ref to the database object can be requested and the creation process of
    the table can be started...
 */


public class PaymentMemoDataSource extends SQLiteOpenHelper{

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "PaymentManager";
    private static final String TABLE_NAME = "Payments";

    private static final String KEY_ID = "id";
    private static final String KEY_COST = "cost";
    private static final String KEY_PRODUCT = "product";

    public PaymentMemoDataSource(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PAYMENTS =
                "CREATE TABLE " + TABLE_NAME +"("+KEY_ID+" INTEGER PRIMARY KEY,"+KEY_COST+" TEXT,"
                        +KEY_PRODUCT+" TEXT"+")";
        db.execSQL(CREATE_TABLE_PAYMENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void addPayment(PaymentMemo p) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COST, p.getCost());
        values.put(KEY_PRODUCT, p.getProduct());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }
}


