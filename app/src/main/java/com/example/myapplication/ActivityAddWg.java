package com.example.myapplication;

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

import java.util.ArrayList;
import java.util.List;

public class ActivityAddWg extends AppCompatActivity {

    Button btnCreateFlat;
    EditText size, flat_name, flatIDText;
    String flatUserName, flatSize, flatName, flatID;
    int flatSizeInt;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    Flats flats;
    ArrayList<String> flatIDs;

    final int MINSIZE = 2;
    final int MAXSIZE = 5;
    final String FIREBASEPATH = "https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        flatIDs = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance(FIREBASEPATH);
        databaseReference = database.getReference("Flats");
        getFlatIDinFirebase();
        mAuth = FirebaseAuth.getInstance();

        btnCreateFlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = mAuth.getCurrentUser();
                getInputData();
                createNewFlat();
            }
        });
        }

    private void setupUIComponents() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_addwg);
        btnCreateFlat = findViewById(R.id.btn_addwg);
        flatIDText = findViewById(R.id.wg_profile_name);
        size = findViewById(R.id.size_of_flat);
        flat_name = findViewById(R.id.flat_share_name);
    }

    private void getFlatIDinFirebase(){
        readData(new FirebaseCallback() {
            @Override
            public void onCallback(List<String> list) {
            }
        });
    }

    private interface FirebaseCallback {
        void onCallback(List<String> list);
    }

    // Get the data from Firebase Server online
    private void readData(FirebaseCallback firebaseCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    String s = snap.getValue(Flats.class).getFlatID();
                    flatIDs.add(s);
                }
                // Wait for the server to retrieve the data
                firebaseCallback.onCallback(flatIDs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }

    // Flat ID must be unique
    private boolean flatIDisTaken() {
        boolean b = flatIDs.contains(flatID);
        return b;
    }

    // Flat size must be at least 2 and at most 5 people
    private boolean flatSizeIsValid(){
        boolean b;
        Log.d("debug", String.valueOf(flatSizeInt));
        if(flatSizeInt >= MINSIZE && flatSizeInt <= MAXSIZE){
            b = true;
        } else {
            b = false;
        }
        Log.d("debug", String.valueOf(b));
        return b;
    }

    // Retrieve input data from screen
    private void getInputData() {
        flatSize = size.getText().toString();
        flatName = flat_name.getText().toString();
        flatID = flatIDText.getText().toString();
        flatSizeInt = Integer.valueOf(flatSize);
        flatUserName = user.getEmail();
    }

    // If the two conditions are fulfilled, start the intent accordingly
    private void startTransmission(){
        if(!flatIDisTaken() && flatSizeIsValid()){
            Toast.makeText(ActivityAddWg.this, "Successfully created Flat!", Toast.LENGTH_LONG).show();
            databaseReference.child(flatID).setValue(flats);
            startActivity(new Intent(ActivityAddWg.this, ActivityOverview.class));
        }

        if(flatIDisTaken()){
            Toast.makeText(ActivityAddWg.this, "FlatID already exists. Try another!", Toast.LENGTH_LONG).show();
        }

        if(!flatSizeIsValid()){
            Toast.makeText(ActivityAddWg.this, "Flat size is at least 2 and at most 5 people", Toast.LENGTH_LONG).show();
        }
    }

    // Switch-case to handle different Flats objects for different user input
    // Default: Every other case except 2,3,4,5 - error handling
    private void createNewFlat(){
        switch(flatSizeInt){
            case 2:
                flats = new Flats(flatUserName, "Placeholder2", flatSizeInt, flatName, flatID);
                startTransmission();
                break;
            case 3:
                flats = new Flats(flatUserName, "Placeholder2", "Placeholder3", flatSizeInt, flatName, flatID);
                startTransmission();
                break;
            case 4:
                flats = new Flats(flatUserName, "Placeholder2", "Placeholder3", "Placeholder4", flatSizeInt, flatName, flatID);
                startTransmission();
                break;
            case 5:
                flats = new Flats(flatUserName, "Placeholder2", "Placeholder3", "Placeholder4", "Placeholder5", flatSizeInt, flatName, flatID);
                startTransmission();
                break;
            default:
                startTransmission();
                break;
        }
    }


}