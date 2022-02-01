package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

public class ActivityPaymentOverview extends AppCompatActivity {

    public static final String LOG_TAG = ActivityPaymentOverview.class.getSimpleName();
    private PaymentMemoDataSource dataSource;

    Button button_savepayment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentoverview);

        Log.d(LOG_TAG, "create Datasource.");
        dataSource = new PaymentMemoDataSource(this);

        savePayment();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(LOG_TAG, "opening database...");
        dataSource.open();

        Log.d(LOG_TAG, "show all database entries...");
        //showAllListEntries();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(LOG_TAG, "closing database...");
        dataSource.close();
    }

    private void showAllListEntries() {
        List<PaymentMemo> paymentMemoList = dataSource.getAllPaymentMemos();

        ArrayAdapter<PaymentMemo> paymentMemoArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                paymentMemoList);

        ListView paymentMemosListView = (ListView) findViewById(R.id.listview_payment_memos);
        paymentMemosListView.setAdapter(paymentMemoArrayAdapter);

        Log.d(LOG_TAG, "Content of the Payment;emo: " + paymentMemoList.toString());
        dataSource = new PaymentMemoDataSource(this);
    }


    private void savePayment() {
        button_savepayment = (Button) findViewById(R.id.btn_save_payment);
        final EditText editTextCost = (EditText) findViewById(R.id.insert_costs);
        final EditText editTextPurpose = (EditText) findViewById(R.id.insert_purpose);

        button_savepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String costString = editTextCost.getText().toString();
                String purpose = editTextPurpose.getText().toString();

                if (TextUtils.isEmpty(costString)) {
                    editTextCost.setError(getString(R.string.editText_errorMessage));
                    return;
                }
                /*if (TextUtils.isEmpty(purpose)) {
                    editTextPurpose.setError(getString(R.string.editText_errorMessage));
                    return;
                }*/



                TextView shareBill = (TextView)findViewById(R.id.share_your_bill);
                shareBill.setText(costString + purpose);


                double cost = Double.parseDouble(costString);
                editTextCost.setText("");
                editTextPurpose.setText("");

                System.out.println(cost + " " + purpose);
                System.out.println(costString.getClass().getSimpleName());
                System.out.println(purpose.getClass().getSimpleName());

                dataSource.createPaymentMemo(costString, purpose);



                //List<PaymentMemo> paymentMemoList = dataSource.getAllPaymentMemos();

                //showAllListEntries();

                /*Intent intent = new Intent(getApplicationContext(), ActivityOverview.class);
                startActivity(intent);*/

                /*
                InputMethodManager inputMethodManager;
                inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (getCurrentFocus() != null) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }

                showAllListEntries();
                */
            }
        });
    }
}