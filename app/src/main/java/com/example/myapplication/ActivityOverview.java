package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ActivityOverview extends AppCompatActivity {

    ImageButton button_managePayments;
    //TextView testtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //try to setup and test my stuff
        setContentView(R.layout.activity_overview);
        addListenerOnButton();
        //displaytext();
    }

    public void addListenerOnButton() {

        //final Context context = this;

        button_managePayments = (ImageButton) findViewById(R.id.btn_managePayments);

        button_managePayments.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ActivityOverview.this.startActivity(new Intent(ActivityOverview.this, ActivityPaymentOverview.class));
                //Intent intent = new Intent(getApplicationContext(), ActivityPaymentOverview.class);
                //startActivity(intent);

            }

        });

    }
/*
    public void displaytext(){
        ActivityPaymentOverview inst = new ActivityPaymentOverview();
        testtext = (TextView) findViewById(R.id.last_Payments);
        testtext.setText(inst.getInputCosts()+":"+inst.getInputPurpose());
    }
*/
}