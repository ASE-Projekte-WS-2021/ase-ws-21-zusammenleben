package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ActivityLogin extends AppCompatActivity {

    EditText username, password;
    TextView signup;
    Button login;
    LoginDBHelper UserDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        signup = (TextView) findViewById(R.id.login_signup);
        login = (Button) findViewById(R.id.login);
        UserDB = new LoginDBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),ActivitySignup.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals(""))
                    Toast.makeText(ActivityLogin.this, "Bitte geben Sie ihr Daten an.", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = UserDB.checkusernamepassword(user, pass);
                    if(checkuserpass==true){
                        Toast.makeText(ActivityLogin.this, "Anmeldung erfolgreich", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ActivityStartScreen.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ActivityLogin.this, "Ungültige Anmeldedaten", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
