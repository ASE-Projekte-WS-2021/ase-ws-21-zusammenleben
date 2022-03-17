package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityForgotPassword extends AppCompatActivity {

    private EditText emailforpassword;
    private Button resetpassword;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        emailforpassword = findViewById(R.id.forgot_password_email);
        resetpassword = findViewById(R.id.btn_resetPassword);
        progressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordreset();
            }
        });
    }

    private void passwordreset() {

        String email = emailforpassword.getText().toString().trim();

        if(email.isEmpty()){
            emailforpassword.setError("Please, enter a email");
            emailforpassword.setFocusable(true);
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailforpassword.setError("Please, enter a correct email");
            emailforpassword.setFocusable(true);
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ActivityForgotPassword.this, "Please, check your emails", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ActivityForgotPassword.this, ActivityLogin.class));
                }else{
                    Toast.makeText(ActivityForgotPassword.this, "Sending email failed! Try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}