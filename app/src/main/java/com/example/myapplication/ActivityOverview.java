package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityOverview extends AppCompatActivity {

    ImageButton button_managePayments;
    ImageView optionButton;
    BottomNavigationView bottomNavigationView;
    String userEmail;
    String flatID;

    TextView paymentPurpose, costs;
    Object cost, purpose;

    FirebaseDatabase database;
    DatabaseReference databaseReferenceFlat;
    DatabaseReference databaseReferencePayment;
    FirebaseAuth mAuth;
    FirebaseUser user;

    ArrayList<ArrayList<String>> flatContents = new ArrayList<>();
    String[] content;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        initFirebase();
        addListenerOnButton();
        getFlatIDinFirebase();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void updateTextView() {
        databaseReferencePayment.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if(flatID.equals(dataSnapshot.child("flat").getValue())) {
                            cost = dataSnapshot.child("cost").getValue();
                            purpose = dataSnapshot.child("purpose").getValue();
                            paymentPurpose.setText(purpose.toString());
                            costs.setText(cost.toString());
                            Log.d("debug", cost.toString() + "--" + purpose.toString());
                        return;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("catch", "Database still empty.....");
            }
        });
    }

    private void deletePayment() {
        databaseReferencePayment.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if(flatID.equals(dataSnapshot.child("flat").getValue())) {
                            dataSnapshot.getRef().removeValue();
                            Intent i = new Intent(ActivityOverview.this, ActivityOverview.class);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(i);
                            overridePendingTransition(0, 0);
                            return;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("catch", "Database still empty.....");
            }
        });
    }

    private void getFlatIDinFirebase(){
        readData(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<ArrayList<String>> list) {
            }
        });
    }

    private interface FirebaseCallback {
        void onCallback(ArrayList<ArrayList<String>> list);
    }

    // Get the data from Firebase Server online
    private void readData(FirebaseCallback firebaseCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    int flatSize = snap.getValue(Flats.class).getFlatSize();
                    ArrayList<String> flatContent = snap.getValue(Flats.class).getData(flatSize);
                    flatContents.add(flatContent);
                }
                // Wait for the server to retrieve the data
                firebaseCallback.onCallback(flatContents);
                getCurrentUserFlat();
                updateTextView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        databaseReferenceFlat.addListenerForSingleValueEvent(valueEventListener);

    }

    private void getCurrentUserFlat(){
        for(int i = 0; i < flatContents.size(); i++){
            if(flatContents.get(i).contains(userEmail)) {
                String currentUserFlat = flatContents.get(i).toString();
                content = currentUserFlat.split(",");
            }
        }
        flatID = extractFlatID();
    }

    private String extractFlatID(){
        for(int i = 0 ; i < content.length ; i++){
            String s = content[i];
            s = s.trim();
            Log.d("debug", s);
        }
        return content[0].substring(1);
    }

    public void addListenerOnButton() {
        button_managePayments.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityPaymentOverview.class);
                startActivity(intent);
            }
        });

        optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(view.getContext(),view);
                popupMenu.setGravity(Gravity.END);
                popupMenu.getMenu().add("Edit Payment").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        return false;
                    }
                });

                popupMenu.getMenu().add("Delete Payment").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        deletePayment();
                            /*@Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(view.getContext(), "Deleted!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                            }
                        });*/
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void setupNavBar(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.payment:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),ActivityStartScreen.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(),ActivityUserProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_overview);
        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.payment);
        button_managePayments = findViewById(R.id.btn_managePayments);
        costs = findViewById(R.id.costs_overview);
        paymentPurpose = findViewById(R.id.payment_purpose);
        optionButton = findViewById(R.id.optionbutton);
        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.payment);
        setupNavBar();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initFirebase(){
        database = FirebaseDatabase.getInstance("https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReferencePayment = database.getReference("Payments");
        databaseReferenceFlat = database.getReference("Flats");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userEmail = user.getEmail();
        assert user != null;
    }
}