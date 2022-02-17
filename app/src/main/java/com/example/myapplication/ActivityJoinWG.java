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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinwg);
        setupUIComponents();

        joinWG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/");
                databaseReference = database.getReference("Flats");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String s = String.valueOf(dataSnapshot.getValue());
                            Log.d("WG", s);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
}
