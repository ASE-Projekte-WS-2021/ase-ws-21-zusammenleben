package com.example.myapplication;

//package com.google.firebase.referencecode.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.referencecode.database.models.Post;
//import com.google.firebase.referencecode.database.models.User;

import java.util.HashMap;
import java.util.Map;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityAddWg extends AppCompatActivity {

    Button addwg;
    EditText userName, address, size, flat_name;
    String flatUserName, flatAddress, flatSize, flatName;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = database.getReference("Flats");

        addwg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flatUserName = userName.getText().toString();
                flatAddress = address.getText().toString();
                flatSize = size.getText().toString();
                flatName = flat_name.getText().toString();

                Flats flats;

                switch (flatSize){
                    case "2":
                        Log.d("WG size: ", "2");
                        flats = new Flats(flatUserName, "Placeholder2", flatAddress, flatSize, flatName);
                        databaseReference.push().setValue(flats);
                        Toast.makeText(ActivityAddWg.this, "Data inserted!", Toast.LENGTH_LONG).show();
                        break;
                    case "3":
                        Log.d("WG size: ", "3");
                        flats = new Flats(flatUserName, "Placeholder2", "Placeholder3",flatAddress, flatSize, flatName);
                        databaseReference.push().setValue(flats);
                        Toast.makeText(ActivityAddWg.this, "Data inserted!", Toast.LENGTH_LONG).show();
                        break;
                    case "4":
                        Log.d("WG Size: ", "4");
                        flats = new Flats(flatUserName, "Placeholder2", "Placeholder3", "Placeholder4", flatAddress, flatSize, flatName);
                        databaseReference.push().setValue(flats);
                        Toast.makeText(ActivityAddWg.this, "Data inserted!", Toast.LENGTH_LONG).show();
                        break;
                    case "5":
                        Log.d("WG Size: ", "5");
                        flats = new Flats(flatUserName, "Placeholder2", "Placeholder3", "Placeholder4", "Placeholder5", flatAddress, flatSize, flatName);
                        databaseReference.push().setValue(flats);
                        Toast.makeText(ActivityAddWg.this, "Data inserted!", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }


    private void setupUIComponents(){
        setContentView(R.layout.activity_addwg);
        addwg = findViewById(R.id.btn_addwg);
        userName = findViewById(R.id.person_name);
        address = findViewById(R.id.address);
        size = findViewById(R.id.size_of_flat);
        flat_name = findViewById(R.id.flat_share_name);
    }
}