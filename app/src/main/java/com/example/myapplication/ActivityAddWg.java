package com.example.myapplication;

//package com.google.firebase.referencecode.database;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.referencecode.database.models.Post;
//import com.google.firebase.referencecode.database.models.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    EditText address, size, flat_name, flatIDText;
    String flatUserName, flatAddress, flatSize, flatName, flatID;
    int numFlatSize;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    long maxId;
    ArrayList<String> flatIDs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = database.getReference("Flats");
        getFlatIDsinFirebase();
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
                flatID = flatIDText.getText().toString();

                Flats flats;
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        maxId = (snapshot.getChildrenCount());
                        Log.d("maxId", String.valueOf(maxId));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                switch (flatSize){
                    case "2":
                        flats = new Flats(flatUserName, "Placeholder2", flatAddress, numFlatSize, flatName, flatID);
                        if(flatIDisTaken()){
                            Toast.makeText(ActivityAddWg.this, "Your Flat ID is already taken. Please try another!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ActivityAddWg.this, "Successfully created Flat!", Toast.LENGTH_LONG).show();
                            databaseReference.child(flatID).setValue(flats);
                            startActivity(new Intent(ActivityAddWg.this, ActivityOverview.class));
                        }
                        break;
                    case "3":
                        flats = new Flats(flatUserName, "Placeholder2", "Placeholder3",flatAddress, numFlatSize, flatName, flatID);
                        if(flatIDisTaken()){
                            Toast.makeText(ActivityAddWg.this, "Your Flat ID is already taken. Please try another!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ActivityAddWg.this, "Successfully created Flat!", Toast.LENGTH_LONG).show();
                            databaseReference.child(flatID).setValue(flats);
                            startActivity(new Intent(ActivityAddWg.this, ActivityOverview.class));
                        }
                        break;
                    case "4":
                        flats = new Flats(flatUserName, "Placeholder2", "Placeholder3", "Placeholder4", flatAddress, numFlatSize, flatName, flatID);
                        if(flatIDisTaken()){
                            Toast.makeText(ActivityAddWg.this, "Your Flat ID is already taken. Please try another!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ActivityAddWg.this, "Successfully created Flat!", Toast.LENGTH_LONG).show();
                            databaseReference.child(flatID).setValue(flats);
                            startActivity(new Intent(ActivityAddWg.this, ActivityOverview.class));
                        }
                        break;
                    case "5":
                        flats = new Flats(flatUserName, "Placeholder2", "Placeholder3", "Placeholder4", "Placeholder5", flatAddress, numFlatSize, flatName, flatID);
                        if(flatIDisTaken()){
                            Toast.makeText(ActivityAddWg.this, "Your Flat ID is already taken. Please try another!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ActivityAddWg.this, "Successfully created Flat!", Toast.LENGTH_LONG).show();
                            databaseReference.child(flatID).setValue(flats);
                            startActivity(new Intent(ActivityAddWg.this, ActivityOverview.class));
                        }
                        break;
                        }
                }
        });
    }

    private void getFlatIDsinFirebase(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    String s = snap.getValue(Flats.class).getProfileName();
                    flatIDs.add(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean flatIDisTaken(){
        return flatIDs.contains(flatID);
    }


    private void setupUIComponents(){
        setContentView(R.layout.activity_addwg);
        addwg = findViewById(R.id.btn_addwg);
        flatIDText = findViewById(R.id.wg_profile_name);
        address = findViewById(R.id.address);
        size = findViewById(R.id.size_of_flat);
        flat_name = findViewById(R.id.flat_share_name);
    }
}