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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivitySignup extends AppCompatActivity {

    EditText signupemail, signuppassword;
    Button createAccount;

    //declare instance FirebaseAuth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");
        //implement BackButton
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

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

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, start register activity
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(ActivitySignup.this, "Registered...\n"+user.getEmail(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ActivitySignup.this, ActivityStartScreen.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(ActivitySignup.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }
                    }
                }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivitySignup.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) ;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //return to previous screen/activity
        return super.onSupportNavigateUp();
    }
}
