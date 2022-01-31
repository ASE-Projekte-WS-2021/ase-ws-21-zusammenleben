package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityPaymentOverview extends AppCompatActivity {

    Button button_savepayment;
    EditText input_purpose, input_costs;

    TextView testtext = (TextView) findViewById(R.id.share_your_bill);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //try to setup and test my stuff
        setContentView(R.layout.activity_paymentoverview);
        addListenerOnButton();
        getInputCosts();
        getInputPurpose();
        //displaytext();
    }

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

    public String getInputCosts(){
        input_costs = (EditText)findViewById(R.id.insert_costs);
        return input_costs.getText().toString();
    }

    public String getInputPurpose(){
        input_purpose = (EditText)findViewById(R.id.insert_purpose);
        return input_purpose.getText().toString();
    }
/*
    public void displaytext(){
        testtext.setText(getInputCosts()+":"+getInputPurpose());
    }
*/
}