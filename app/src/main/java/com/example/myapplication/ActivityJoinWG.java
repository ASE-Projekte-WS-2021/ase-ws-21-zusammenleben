package com.example.myapplication;

//package com.google.firebase.referencecode.database;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import androidx.appcompat.app.AppCompatActivity;

//import com.google.firebase.referencecode.database.models.Post;
//import com.google.firebase.referencecode.database.models.User;

public class ActivityJoinWG extends AppCompatActivity {

    Button joinWG;
    EditText userName;
    TextView flatSize, flatName;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();

        joinWG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityOverview.class);
                startActivity(intent);
            }
        });
    }

    private void setupUIComponents() {
        setContentView(R.layout.activity_joinwg);
        joinWG = findViewById(R.id.btn_addwg);
        userName = findViewById(R.id.person_name);
        flatSize = findViewById(R.id.current_size_of_flat);
        flatName = findViewById(R.id.flat_name);
    }
}
