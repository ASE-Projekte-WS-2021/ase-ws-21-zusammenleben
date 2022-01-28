package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityOverview extends AppCompatActivity {

    Button button_managePayments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //try to setup and test my stuff
        setContentView(R.layout.activity_overview);
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        final Context context = this;

        button_managePayments = (Button) findViewById(R.id.btn_managePayments);

        button_managePayments.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, ActivityPaymentOverview.class);
                startActivity(intent);

            }

        });

    }
}