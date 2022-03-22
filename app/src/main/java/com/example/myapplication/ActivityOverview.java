package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

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

    ArrayList<PaymentMemo> paymentList = new ArrayList<>();
    ArrayList<PaymentMemo> extractedPaymentList = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        initFirebase();
        addListenerOnButton();
        getFlatIDinFirebase();
    }


    private void updateTextView() {
        databaseReferencePayment.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Log.d("hi", "yo");
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
        readData(new FirstFirebaseCallback() {
            @Override
            public void onFirstCallback(ArrayList<ArrayList<String>> list) {
                // try something
            }
        });
    }

    private interface FirstFirebaseCallback {
        void onFirstCallback(ArrayList<ArrayList<String>> list);
    }

    // Get the data from Firebase Server online
    private void readData(FirstFirebaseCallback firebaseCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    int flatSize = snap.getValue(Flats.class).getFlatSize();
                    ArrayList<String> flatContent = snap.getValue(Flats.class).getData(flatSize);
                    flatContents.add(flatContent);
                }
                // Wait for the server to retrieve the data
                firebaseCallback.onFirstCallback(flatContents);
                getCurrentUserFlat();
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
        optionButton = findViewById(R.id.optionbutton);
        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.payment);

        recyclerView = findViewById(R.id.recyclerview_payments);

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

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("before Callback", "hier!");
        readDataFromPayments(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<PaymentMemo> list) {
                fillRecyclerView();
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        paymentList.clear();
    }


    private void fillRecyclerView() {
        Iterator itr = paymentList.iterator();
        String userEmail = mAuth.getCurrentUser().getEmail();
        while(itr.hasNext()){
            PaymentMemo p = (PaymentMemo) itr.next();
            if(!p.getReceiverName().contains(userEmail)){
                itr.remove();
            }
        }

        Log.d("final:", String.valueOf(paymentList.size()));


        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecycleViewAdapter(paymentList, this);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new );
    }


    private void readDataFromPayments(FirebaseCallback firebaseCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String paymentID = ds.getKey();
                    Log.d("paymentID", paymentID);
                    double paymentCost = ds.getValue(PaymentMemo.class).getCost();
                    String paymentPurpose = ds.getValue(PaymentMemo.class).getPurpose();
                    String paymentEmail = ds.getValue(PaymentMemo.class).getEmail();
                    String paymentReceiver = ds.getValue(PaymentMemo.class).getReceiverName();
                    String paymentFlat = ds.getValue(PaymentMemo.class).getFlat();
                    PaymentMemo payment = new PaymentMemo(paymentCost, paymentPurpose, paymentEmail, paymentReceiver, paymentFlat);
                    paymentList.add(payment);
                }
                firebaseCallback.onCallback(paymentList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReferencePayment.addListenerForSingleValueEvent(valueEventListener);
    }

    private interface FirebaseCallback {
        void onCallback(ArrayList<PaymentMemo> myList);
    }


}