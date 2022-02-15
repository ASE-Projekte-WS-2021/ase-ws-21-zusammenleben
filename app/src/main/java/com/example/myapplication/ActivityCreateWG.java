package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class ActivityCreateWG extends AppCompatActivity {

    Button button_createNewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //try to setup and test my stuff
        setContentView(R.layout.activity_createwg);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

        button_createNewGroup = (Button) findViewById(R.id.btn_createNewGroup);

        button_createNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),ActivityAddWg.class);
                startActivity(intent);
            }
        });
    }


    }

