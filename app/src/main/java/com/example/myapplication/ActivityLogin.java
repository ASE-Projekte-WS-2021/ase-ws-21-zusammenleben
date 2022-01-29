package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityLogin extends AppCompatActivity {

    EditText username, password;
    TextView signup;
    Button button_login;
    LoginDBHelper UserDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        signup = (TextView) findViewById(R.id.login_signup);
        button_login = (Button) findViewById(R.id.btn_login);
        UserDB = new LoginDBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),ActivitySignup.class);
                startActivity(intent);
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals(""))
                    Toast.makeText(ActivityLogin.this, "Please enter your data.", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = UserDB.checkusernamepassword(user, pass);
                    if(checkuserpass==true){
                        Toast.makeText(ActivityLogin.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ActivityStartScreen.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ActivityLogin.this, "Data invalid!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
