package com.example.myapplication;

//package com.google.firebase.referencecode.database;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import com.google.firebase.referencecode.database.models.Post;
//import com.google.firebase.referencecode.database.models.User;

public class ActivityJoinWG extends AppCompatActivity {

    Button joinWG;
    EditText userName, flatName;
    TextView flatSize;
    DatabaseReference databaseReference;
    Object allFlats;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinwg);
        setupUIComponents();
        database = FirebaseDatabase.getInstance("https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = database.getReference("Flats");
        getDataFromFirebase();

        joinWG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("234", "hey");
                Intent intent = new Intent(getApplicationContext(), ActivityOverview.class);
                startActivity(intent);
            }
        });
    }

    private void setupUIComponents() {
        joinWG = findViewById(R.id.btn_joinwg);
        userName = findViewById(R.id.person_name);
        flatSize = findViewById(R.id.current_size_of_flat);
        flatName = findViewById(R.id.flat_name);
    }

    private void getDataFromFirebase(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(snapshot.exists())
                        for (DataSnapshot snap : snapshot.getChildren()){
                            allFlats = snap.getValue();
                            Log.d("flats", allFlats.toString());
                            /** TODO : allFlats hat alle WG's. Damit kann man weiterarbeiten und die Daten verwandeln....funktioniert auch alles hier in dieser Methode
                }
                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("catch", "error!");
            }
            });

    }
}
