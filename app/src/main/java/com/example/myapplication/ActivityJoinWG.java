package com.example.myapplication;

//package com.google.firebase.referencecode.database;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.entities.Flats;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

//import com.google.firebase.referencecode.database.models.Post;
//import com.google.firebase.referencecode.database.models.User;

public class ActivityJoinWG extends AppCompatActivity {

    Button joinWG, findWG;
    EditText userName, flatName;
    TextView foundFlatAddress, foundFlatOwner,foundFlatPeople;
    String userFlatNameInput;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    boolean check = true;

    int flatSize;
    HashMap <String, String> userMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_joinwg);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setupUIComponents();
        database = FirebaseDatabase.getInstance("https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = database.getReference("Flats");
        mAuth = FirebaseAuth.getInstance();


        joinWG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserToFlat();
            }
        });

        findWG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userFlatNameInput = flatName.getText().toString();
                Log.d("debug", userFlatNameInput);
                retrieveData();
            }
        });
    }

    private void addUserToFlat(){
        userFlatNameInput = flatName.getText().toString();
        for (Map.Entry<String, String> map : userMap.entrySet()){
            String key = map.getKey();
            String value = map.getValue();
            if (value.contains("Placeholder") && check){
                FirebaseUser user = mAuth.getCurrentUser();
                databaseReference.child(userFlatNameInput).child(key).setValue(user.getEmail());
                Intent intent = new Intent(getApplicationContext(), ActivityOverview.class);
                startActivity(intent);
                check = false;
            }
        }
    }





    private void retrieveData() {
        Log.d("debug2", userFlatNameInput);
        userMap = new HashMap<>();
        Query checkFlatName = databaseReference.orderByChild("flatID").equalTo(userFlatNameInput);

        checkFlatName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    if(snap.getValue(Flats.class).getFlatID().equals(userFlatNameInput)){
                        Toast.makeText(ActivityJoinWG.this, "WG existiert!", Toast.LENGTH_LONG).show();
                        foundFlatOwner.setText("Gründer: " + snap.getValue(Flats.class).getFirstUser());
                        foundFlatPeople.setText("Mitbewohner: " + snap.getValue(Flats.class).getFlatSize());
                        flatSize = snap.getValue(Flats.class).getFlatSize();

                        switch(flatSize) {
                            case 2:
                                userMap.put("secondUser", snap.getValue(Flats.class).getSecondUser());
                                break;
                            case 3:
                                userMap.put("secondUser", snap.getValue(Flats.class).getSecondUser());
                                userMap.put("thirdUser", snap.getValue(Flats.class).getThirdUser());
                                break;
                            case 4:
                                userMap.put("secondUser", snap.getValue(Flats.class).getSecondUser());
                                userMap.put("thirdUser", snap.getValue(Flats.class).getThirdUser());
                                userMap.put("fourthUser", snap.getValue(Flats.class).getFourthUser());
                                break;
                            case 5:
                                userMap.put("secondUser", snap.getValue(Flats.class).getSecondUser());
                                userMap.put("thirdUser", snap.getValue(Flats.class).getThirdUser());
                                userMap.put("fourthUser", snap.getValue(Flats.class).getFourthUser());
                                userMap.put("fifthUser", snap.getValue(Flats.class).getFifthUser());
                                break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setupUIComponents() {
        setContentView(R.layout.activity_joinwg);
        joinWG = findViewById(R.id.btn_joinwg);
        flatName = findViewById(R.id.flat_name);
        findWG = findViewById(R.id.btn_findwg);


        foundFlatAddress = findViewById(R.id.found_flat_address);
        foundFlatOwner = findViewById(R.id.found_flat_owner);
        foundFlatPeople = findViewById(R.id.found_flat_people);

    }

}

//#TODO update eingegebene Flat Name in User Profil anstatt Home Appartment
//#TODO User Profil Buttons sind nicht richtig beschriftet Invite your Friends
//#TODO User Namen anstatt Email einblenden, Name wird doppelt hinterlegt einaml beim Sign up einmal bei der erstellung der WG
//#TODO Daten werden noc nicht von der Erstellung der Zahlung zu der Zahlungsübersicht übertragen
//#TODO was kommt in das Overview display?
//#TODO WGs anzeigen die in der Datenbank gespeichert sind damit man einer beitreten kann und ein Limit für die Beitritte setzen
