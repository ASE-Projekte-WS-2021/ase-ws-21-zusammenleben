package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

public class ActivityAddPayment extends AppCompatActivity {

    Button button_overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpayment);
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        final Context context = this;

        button_overview = (Button) findViewById(R.id.button_addpayment);

        button_overview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, ActivityPaymentOverview.class);
                startActivity(intent);

            }

        });

    }

}