package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActivitySignup extends AppCompatActivity {

    EditText signupemail, signuppassword;
    Button createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");
        //implement BackButton
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // init our views
        signupemail = findViewById(R.id.signup_e_mail);
        signuppassword = findViewById(R.id.signup_password);
        createAccount = findViewById(R.id.btn_createAccount);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = signupemail.getText().toString().trim();
                String password = signuppassword.getText().toString().trim();
                // check email validation
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    signupemail.setError("Wrong E-Mail");
                    signupemail.setFocusable(true);
                }
                else if (password.length()<8){
                    // check password
                    signuppassword.setError("Password must be 8 characters");
                    signuppassword.setFocusable(true);
                }
                else {
                    registerUser (email, password);
                }
            }
        });
    }

    private void registerUser(String email, String password) {
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //return to previous screen/activity
        return super.onSupportNavigateUp();
    }
}
