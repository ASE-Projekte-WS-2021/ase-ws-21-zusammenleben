package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityForgotPassword extends AppCompatActivity {

    EditText emailforpassword;
    Button resetpassword;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        resetpassword();
        initFirebase();
    }

    private void passwordreset() {

        String email = emailforpassword.getText().toString().trim();

        if(email.isEmpty()){
            emailforpassword.setError("Please, enter your email!");
            emailforpassword.setFocusable(true);
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailforpassword.setError("Please, enter your email!");
            emailforpassword.setFocusable(true);
            return;
        }

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Sending email");
        pd.show();

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Toast.makeText(ActivityForgotPassword.this, "Please, check your emails!", Toast.LENGTH_LONG).show();
                    pd.dismiss();
                    startActivity(new Intent(ActivityForgotPassword.this, ActivityLogin.class));
                }else{
                    Toast.makeText(ActivityForgotPassword.this, "The email does not exist. Please try again.", Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
            }
        });
    }

    public void resetpassword() {
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordreset();
            }
        });
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_forgotpassword);
        emailforpassword = findViewById(R.id.forgot_password_email);
        resetpassword = findViewById(R.id.btn_resetPassword);
    }

    private void initFirebase(){
        mAuth = FirebaseAuth.getInstance();
    }
}