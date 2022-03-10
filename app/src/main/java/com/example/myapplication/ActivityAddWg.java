package com.example.myapplication;

//package com.google.firebase.referencecode.database;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//import com.google.firebase.referencecode.database.models.Post;
//import com.google.firebase.referencecode.database.models.User;

public class ActivityAddWg extends AppCompatActivity {

    Button addwg;
    EditText userName, address, size, flat_name;
    String flatUserName, flatAddress, flatSize, flatName;
    int numFlatSize;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    long maxId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = database.getReference("Flats");
        mAuth = FirebaseAuth.getInstance();

        addwg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //flatUserName = userName.getText().toString();
                flatAddress = address.getText().toString();
                flatSize = size.getText().toString();
                numFlatSize = Integer.valueOf(flatSize);
                flatName = flat_name.getText().toString();
                FirebaseUser user = mAuth.getCurrentUser();
                flatUserName = user.getEmail();

                Flats flats;
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        maxId = (snapshot.getChildrenCount());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                switch (flatSize){
                    case "2":
                        Log.d("Ds", flatAddress);
                        Log.d("WG size: ", "2");
                        flats = new Flats(flatUserName, "Placeholder2", flatAddress, numFlatSize, flatName);
                        String flatCounter = "F" + String.valueOf(maxId+1);
                        databaseReference.child(flatCounter).setValue(flats);
                        Toast.makeText(ActivityAddWg.this, "Data inserted!", Toast.LENGTH_LONG).show();
                        System.out.println(numFlatSize);
                        break;
                    case "3":
                        Log.d("WG size: ", "3");
                        flats = new Flats(flatUserName, "Placeholder2", "Placeholder3",flatAddress, numFlatSize, flatName);
                        flatCounter = "F" + String.valueOf(maxId+1);
                        databaseReference.child(flatCounter).setValue(flats);
                        Toast.makeText(ActivityAddWg.this, "Data inserted!", Toast.LENGTH_LONG).show();
                        break;
                    case "4":
                        Log.d("WG Size: ", "4");
                        flats = new Flats(flatUserName, "Placeholder2", "Placeholder3", "Placeholder4", flatAddress, numFlatSize, flatName);
                        flatCounter = "F" + String.valueOf(maxId+1);
                        databaseReference.child(flatCounter).setValue(flats);
                        Toast.makeText(ActivityAddWg.this, "Data inserted!", Toast.LENGTH_LONG).show();
                        break;
                    case "5":
                        Log.d("WG Size: ", "5");
                        flats = new Flats(flatUserName, "Placeholder2", "Placeholder3", "Placeholder4", "Placeholder5", flatAddress, numFlatSize, flatName);
                        flatCounter = "F" + String.valueOf(maxId+1);
                        databaseReference.child(flatCounter).setValue(flats);
                        Toast.makeText(ActivityAddWg.this, "Data inserted!", Toast.LENGTH_LONG).show();
                        break;
                }

                startActivity(new Intent(ActivityAddWg.this, ActivityOverview.class));

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