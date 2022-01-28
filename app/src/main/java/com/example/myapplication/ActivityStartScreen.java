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

    Button button_saGroup1;
    Button button_createNewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //try to setup and test my stuff
        setContentView(R.layout.activity_startscreen);
        addListenerOnButton();

        //database test stuff
        PaymentMemo testMemo = new PaymentMemo("Stuff", 5, 3.50, 101);
        Log.d(LOG_TAG, "Content of the Testmemo: " + testMemo.toString());
        dataSource = new PaymentMemoDataSource(this);

        Log.d(LOG_TAG, "Opening Datasource.");
        dataSource = new PaymentMemoDataSource(this);

        Log.d(LOG_TAG, "Closing Datasource.");
        dataSource.close();
    }

    public void addListenerOnButton() {

        final Context context = this;

        button_saGroup1 = (Button) findViewById(R.id.btn_sAGroup1);
        button_createNewGroup = (Button) findViewById(R.id.btn_createNewGroup);

        button_saGroup1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, ActivityOverview.class);
                startActivity(intent);

            }

        });

        button_createNewGroup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, ActivityUserProfile.class);
                startActivity(intent);

            }

        });

    }
}