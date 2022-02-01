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
import android.widget.Toast;

import java.util.List;

public class ActivityPaymentOverview extends AppCompatActivity {

    // For testing the database
    public static final String LOG_TAG = ActivityStartScreen.class.getSimpleName();
    private PaymentMemoDataSource dataSource;
    private PaymentMemo payment;
    Button button_savepayment;
    EditText editTextCost, editTextPurpose;
/*
    public ActivityPaymentOverview(EditText editTextCost, EditText editTextPurpose) {
        this.editTextCost = editTextCost;
        this.editTextPurpose = editTextPurpose;
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //try to setup and test my stuff
        setContentView(R.layout.activity_paymentoverview);

        /*
        //database test stuff
        PaymentMemo testMemo = new PaymentMemo("Stuff", 5, 3.50, 101);
        Log.d(LOG_TAG, "Content of the Testmemo: " + testMemo.toString());
        dataSource = new PaymentMemoDataSource(this);
        */

        Log.d(LOG_TAG, "Opening Datasource.");
        dataSource = new PaymentMemoDataSource(this);

        button_savepayment = (Button) findViewById(R.id.btn_save_payment);
        editTextCost = (EditText) findViewById(R.id.insert_costs);
        editTextPurpose = (EditText) findViewById(R.id.insert_purpose);

        /*
        Log.d(LOG_TAG, "Closing Datasource.");
        dataSource.close();
        */

        savePayment();

    }
/*
    @Override
    protected void onResume() {
        super.onResume();

        Log.d(LOG_TAG, "opening database...");
        dataSource.open();

        Log.d(LOG_TAG, "show all database entries...");
        showAllListEntries(); //method will be finished later
    }
*/
    @Override
    protected void onPause() {
        super.onPause();

        Log.d(LOG_TAG, "closing database...");
        dataSource.close();
    }

   /*
    public void addListenerOnButton() {

        final Context context = this;

        button_savepayment = (Button) findViewById(R.id.btn_save_payment);

        button_savepayment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //get and save input from input_purpose and input_costs into db
                //Intent intent = new Intent(context, ActivityPaymentOverview.class);
                //startActivity(intent);
                //testtext.setText(getInputCosts()+":"+getInputPurpose());
                ActivityPaymentOverview.this.startActivity(new Intent(ActivityPaymentOverview.this, ActivityOverview.class));
            }

        });

    }
    */
/*
    public String getInputCosts(){
        input_costs = (EditText)findViewById(R.id.insert_costs);
        return input_costs.getText().toString();
    }

    public String getInputPurpose(){
        input_purpose = (EditText)findViewById(R.id.insert_purpose);
        return input_purpose.getText().toString();
    }
*/

private void savePayment() {
        button_savepayment = (Button) findViewById(R.id.btn_save_payment);
        final EditText editTextCost = (EditText) findViewById(R.id.insert_costs);
        final EditText editTextPurpose = (EditText) findViewById(R.id.insert_purpose);

        button_savepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String costString = editTextCost.getText().toString();
                String purpose = editTextPurpose.getText().toString();
                TextView shareBill = (TextView)findViewById(R.id.share_your_bill);
                shareBill.setText(costString + purpose);

                payment = new PaymentMemo(costString, purpose);
                dataSource.addPayment(payment);


                System.out.println("Successfull!");
                Toast.makeText(getApplicationContext(), "Successfull!",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), ActivityOverview.class);
                startActivity(intent);

           //     InputMethodManager inputMethodManager;
             //   inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
              //  if(getCurrentFocus() != null) {
              //      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }


        });
    }
/*
    private void showAllListEntries (){
        List<PaymentMemo> paymentMemoList = dataSource.getAllPaymentMemos();

        ArrayAdapter<PaymentMemo> paymentMemoArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                paymentMemoList);

        ListView paymentMemosListView = (ListView) findViewById(R.id.listview_payment_memos);
        paymentMemosListView.setAdapter(paymentMemoArrayAdapter);

    }*/
}

