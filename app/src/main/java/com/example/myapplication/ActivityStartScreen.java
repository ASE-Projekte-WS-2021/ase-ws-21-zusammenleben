package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ActivityStartScreen extends AppCompatActivity {

    // For testing the database
    public static final String LOG_TAG = ActivityStartScreen.class.getSimpleName();
    private PaymentMemoDataSource dataSource;
    Button button_newPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //try to setup and test my stuff
        setContentView(R.layout.activity_startscreen);
        button_newPayment = (Button) findViewById(R.id.btn_newPayment);

        button_newPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),ActivityOverview.class);
                startActivity(intent);
            }
        });


        //database test stuff
        PaymentMemo testMemo = new PaymentMemo("Stuff", 5, 3.50, 101);
        Log.d(LOG_TAG, "Content of the Testmemo: " + testMemo.toString());
        dataSource = new PaymentMemoDataSource(this);

        Log.d(LOG_TAG, "Opening Datasource.");
        dataSource = new PaymentMemoDataSource(this);

        Log.d(LOG_TAG, "Closing Datasource.");
        dataSource.close();
    }

    }