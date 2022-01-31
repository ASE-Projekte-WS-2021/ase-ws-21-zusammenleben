package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

 /* this class is the Datasource and keeps the connection to the database.
    A ref to the database object can be requested and the creation process of
    the table can be started...
 */

public class PaymentMemoDataSource {

    private static final String LOG_TAG = PaymentMemoDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private PaymentMemoDbHelper dbHelper;

    private String[] columns = {
            PaymentMemoDbHelper.COLUMN_ID,
            PaymentMemoDbHelper.COLUMN_PRODUCT,
            PaymentMemoDbHelper.COLUMN_QUANTITY,
            PaymentMemoDbHelper.COLUMN_COST
    };

    public PaymentMemoDataSource(Context context) {
        Log.d(LOG_TAG, "The DataSource creates the dbHelper.");
        dbHelper = new PaymentMemoDbHelper(context);
    }

    public void open() {
        Log.d(LOG_TAG, "Reference to database.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Database reference accessed. Path to Database: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Database is closed");
    }

    public PaymentMemo createPaymentMemo(double cost, String product){
        ContentValues values = new ContentValues();
        values.put(PaymentMemoDbHelper.COLUMN_PRODUCT, product);
        //values.put(PaymentMemoDbHelper.COLUMN_QUANTITY, quantity);
        values.put(PaymentMemoDbHelper.COLUMN_COST, cost);

        long insertId = database.insert(PaymentMemoDbHelper.TABLE_PAYMENT_LIST, null, values);

        Cursor cursor = database.query(PaymentMemoDbHelper.TABLE_PAYMENT_LIST, columns, PaymentMemoDbHelper.COLUMN_ID + "=" + insertId, null, null, null, null);
        //cursor moves to first item of db. One can use "moveToLast" equivalent..
        cursor.moveToFirst();
        PaymentMemo paymentMemo = cursorToPaymentMemo(cursor);
        cursor.close();

        return paymentMemo;
    }

    private PaymentMemo cursorToPaymentMemo(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(PaymentMemoDbHelper.COLUMN_ID);
        int idProduct = cursor.getColumnIndex(PaymentMemoDbHelper.COLUMN_PRODUCT);
        int idQuantity = cursor.getColumnIndex(PaymentMemoDbHelper.COLUMN_QUANTITY);
        int idCost = cursor.getColumnIndex(PaymentMemoDbHelper.COLUMN_COST);

        String product = cursor.getString(idProduct);
        int quantity = cursor.getInt(idQuantity);
        double cost = cursor.getDouble(idCost);
        long id = cursor.getLong(idIndex);

        PaymentMemo paymentMemo = new PaymentMemo(product,quantity, cost, id);

        return paymentMemo;
    }

    public List<PaymentMemo> getAllPaymentMemos() {
        List<PaymentMemo> paymentMemoList = new ArrayList<>();

        Cursor cursor = database.query(PaymentMemoDbHelper.TABLE_PAYMENT_LIST, columns, null, null, null, null, null);

        cursor.moveToFirst();
        PaymentMemo paymentMemo;
        while(!cursor.isAfterLast()) {
            paymentMemo = cursorToPaymentMemo(cursor);
            paymentMemoList.add(paymentMemo);
            Log.d(LOG_TAG, "ID: " + paymentMemo.getId() + ", Content: " + paymentMemo.toString());
            cursor.moveToNext();
        }
        cursor.close();
        return paymentMemoList;
    }
}
