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

public class ActivitySignup extends AppCompatActivity {

    EditText username, password, e_mail, repeat_password;
    Button create_account;
    LoginDBHelper UserDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = (EditText) findViewById(R.id.signup_username);
        e_mail = (EditText) findViewById(R.id.signup_e_mail);
        password = (EditText) findViewById(R.id.signup_password);
        repeat_password = (EditText) findViewById(R.id.signup_repassword);
        create_account = (Button) findViewById(R.id.create_Account);
        UserDB = new LoginDBHelper(this);

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String email = e_mail.getText().toString();
                String pass = password.getText().toString();
                String repass = repeat_password.getText().toString();

                if (user.equals("")||email.equals("")||pass.equals("")||repass.equals(""))
                    Toast.makeText(ActivitySignup.this, "Bitte füllen Sie alle notwendige Angaben aus", Toast.LENGTH_SHORT).show();
                else{
                    if (pass.equals(repass)){
                        Boolean checkuser = UserDB.checkusername(user);
                        if (checkuser==false){
                             Boolean insert = UserDB.insertData(user, email, pass);
                             if (insert==true){
                                 Toast.makeText(ActivitySignup.this, "Registrierung erfolgreich", Toast.LENGTH_SHORT).show();
                                 Intent intent = new Intent(getApplicationContext(),ActivityStartScreen.class);
                                 startActivity(intent);
                             } else{
                                 Toast.makeText(ActivitySignup.this, "Registrierung fehlgeschlagen", Toast.LENGTH_SHORT).show();
                             }
                        } else{
                            Toast.makeText(ActivitySignup.this, "Der Benutzer existiert bereits. Bitte melden Sie sich an!", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(ActivitySignup.this, "Die Passwörter stimmen nicht überein.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}
