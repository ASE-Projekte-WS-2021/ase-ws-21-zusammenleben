package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityUserProfile extends AppCompatActivity {

    Button button_abmelden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //try to setup and test my stuff
        setContentView(R.layout.activity_userprofile);
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        final Context context = this;

        button_abmelden = (Button) findViewById(R.id.button);

        button_abmelden.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, ActivityStartScreen.class);
                startActivity(intent);

            }

        });

    }
}