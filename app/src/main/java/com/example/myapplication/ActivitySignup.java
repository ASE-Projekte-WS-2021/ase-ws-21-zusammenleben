package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySignup extends AppCompatActivity {

    EditText username, password, e_mail, repeat_password;
    Button create_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = (EditText) findViewById(R.id.signup_username);
        e_mail = (EditText) findViewById(R.id.signup_e_mail);
        password = (EditText) findViewById(R.id.signup_password);
        repeat_password = (EditText) findViewById(R.id.signup_repassword);
        create_account = (Button) findViewById(R.id.create_Account);

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),ActivityStartScreen.class);
                startActivity(intent);
            }
        });
    }

}
