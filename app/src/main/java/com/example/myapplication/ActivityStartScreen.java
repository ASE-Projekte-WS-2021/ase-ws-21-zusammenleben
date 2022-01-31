package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.inputmethod.InputMethodManager;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class ActivityStartScreen extends AppCompatActivity {


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
    }
}