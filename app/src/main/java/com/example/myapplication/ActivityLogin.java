package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

public class ActivityLogin extends AppCompatActivity {

    EditText loginemail;
    EditText loginpassword;
    Button login;
    TextView signup;
    TextView forgotpassword;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        checkForDynamicLinks();
        signup();
        forgotpassword();
        login();
        initFirebase();

    }

    private void checkForDynamicLinks() {
        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent()).addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
            @Override
            public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                Log.i("ActivityLogin.java", "Link successful!");

                Uri deepLink = null;
                if(pendingDynamicLinkData != null){
                    deepLink = pendingDynamicLinkData.getLink();
                }

                if(deepLink != null){
                    Log.i("ActivityLogin.java", "this is the link" + deepLink.toString());
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("ActivityLogin.java", "could not find link!");
            }
        });
    }

    public void login() {
        /////////Marco

        checkForDynamicLinks();

        //////////

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = loginemail.getText().toString().trim();
                String password = loginpassword.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()){
                    loginemail.setError("Please, enter your email!");
                    loginemail.setFocusable(true);
                } else if (password.isEmpty()){
                    loginpassword.setError("Please, enter your password!");
                    loginpassword.setFocusable(true);
                } else {
                    loginUser (email, password);
                }

            }
        });
    }

    public void forgotpassword() {
        forgotpassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityLogin.this, ActivityForgotPassword.class));
            }
        });
    }

    public void signup() {
        signup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityLogin.this, ActivitySignup.class));
            }
        });
    }

    private void loginUser(String email, String password) {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Login");
        pd.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, and start register activity
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(ActivityLogin.this, ActivityStartScreen.class));
                            pd.dismiss();
                            finish();
                        }
                    }
                }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(ActivityLogin.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) ;
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkForDynamicLinks();
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_login);
        forgotpassword = findViewById(R.id.forgot_password);
        loginemail = findViewById(R.id.login_email);
        loginpassword = findViewById(R.id.login_password);
        login = findViewById(R.id.btn_login);
        signup = findViewById(R.id.login_signup);
    }

    private void initFirebase(){
        mAuth = FirebaseAuth.getInstance();
    }
}

