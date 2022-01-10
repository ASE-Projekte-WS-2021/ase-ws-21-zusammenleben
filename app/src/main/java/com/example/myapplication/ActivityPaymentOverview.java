package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityPaymentOverview extends AppCompatActivity {

    Button button_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //try to setup and test my stuff
        setContentView(R.layout.activity_paymentoverview);
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        final Context context = this;

        button_payment = (Button) findViewById(R.id.button_addpaymenttooverview);

        button_payment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, ActivityAddPayment.class);
                startActivity(intent);

            }

        });

    }
}