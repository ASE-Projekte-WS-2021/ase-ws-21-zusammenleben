package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityPaymentOverview extends AppCompatActivity {

    String purpose;
    int costs;

    Button button_savepayment;
    EditText input_purpose, input_costs;

    TextView testtext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //try to setup and test my stuff
        setContentView(R.layout.activity_paymentoverview);
        addListenerOnButton();

        input_costs = (EditText)findViewById(R.id.insert_costs);
        input_purpose = (EditText)findViewById(R.id.insert_purpose);
        testtext = (TextView) findViewById(R.id.share_your_bill);

        //displaytext();
        //checkForUserInput();
    }
/*
    @Override
    protected void onStart(){
        super.onStart();
        checkForUserInput();
    }*/

    public void addListenerOnButton() {

        final Context context = this;

        button_savepayment = (Button) findViewById(R.id.btn_save_payment);

        button_savepayment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //get and save input from input_purpose and input_costs into db
                Intent intent = new Intent(context, ActivityOverview.class);
                startActivity(intent);
                //ActivityPaymentOverview.this.startActivity(new Intent(ActivityPaymentOverview.this, ActivityOverview.class));
            }

        });

    }
/*
    public void checkForUserInput (){

        if (getInputCosts().isEmpty()){
            Log.i("myApp", "Empty");
        }
        else {getInputCosts();}

        if (getInputPurpose().isEmpty()){
            Log.i("myApp", "EmptyP");
        }
        else { getInputPurpose();}
        displaytext();
    }

    public String getInputCosts(){
        return String.valueOf(costs = Integer.valueOf(input_costs.getText().toString()));
    }

    public String getInputPurpose(){
        return purpose = input_purpose.getText().toString();
    }

    public void displaytext(){
        testtext.setText(getInputCosts()+":"+getInputPurpose());
    }

    @Override
    public void onDestroy() {
        // stop the Service
        stopService(new Intent(this, ActivityPaymentOverview.class));
        super.onDestroy();
    }
*/
}