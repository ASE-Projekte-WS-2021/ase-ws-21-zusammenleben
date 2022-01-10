package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityStartScreen extends AppCompatActivity {

    Button button;
    Button buttonnew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //try to setup and test my stuff
        setContentView(R.layout.activity_startscreen);
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        final Context context = this;

        button = (Button) findViewById(R.id.button2);
        buttonnew = (Button) findViewById(R.id.button6);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, ActivityOverview.class);
                startActivity(intent);

            }

        });

        buttonnew.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, ActivityUserProfile.class);
                startActivity(intent);

            }

        });

    }
}