package com.example.myapplication;

//package com.google.firebase.referencecode.database;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

//import com.google.firebase.referencecode.database.models.Post;
//import com.google.firebase.referencecode.database.models.User;

public class ActivityJoinWG extends AppCompatActivity {

    TextView flatID, flatOwner, flatSize;
    Button btnJoinFlat;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    HashMap <String, String> userMap;

    final String FIREBASEPATH = "https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        initFirebase();
    }

    private void setupUIComponents() {
        setContentView(R.layout.activity_joinwg);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        flatID = findViewById(R.id.found_flat_ID);
        flatOwner = findViewById(R.id.found_flat_owner);
        flatSize = findViewById(R.id.found_flat_people);
        btnJoinFlat = findViewById(R.id.btn_joinwg);
    }

    private void initFirebase() {
        database = FirebaseDatabase.getInstance(FIREBASEPATH);
        databaseReference = database.getReference("Flats");
        mAuth = FirebaseAuth.getInstance();
    }

}

//#TODO update eingegebene Flat Name in User Profil anstatt Home Appartment
//#TODO User Profil Buttons sind nicht richtig beschriftet Invite your Friends
//#TODO User Namen anstatt Email einblenden, Name wird doppelt hinterlegt einaml beim Sign up einmal bei der erstellung der WG
//#TODO Daten werden noc nicht von der Erstellung der Zahlung zu der Zahlungsübersicht übertragen
//#TODO was kommt in das Overview display?
//#TODO WGs anzeigen die in der Datenbank gespeichert sind damit man einer beitreten kann und ein Limit für die Beitritte setzen
