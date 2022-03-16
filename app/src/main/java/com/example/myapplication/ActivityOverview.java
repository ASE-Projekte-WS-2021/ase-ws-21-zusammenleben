package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityOverview extends AppCompatActivity {

    ImageButton button_managePayments;
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;
    String userEmail;
    private CircleImageView circleImageView;

    TextView paymentPurpose, costs;
    Object cost, purpose;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference("Payments");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        addListenerOnButton();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        circleImageView =findViewById(R.id.show_picture_overwiew);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        userEmail = user.getEmail();


        if (user.getPhotoUrl() != null ){
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .into(circleImageView);
        }

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ActivityUserProfile.class));
            }
        });



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
                    case R.id.shopping:
                        startActivity(new Intent(getApplicationContext(),ActivityShoppingList.class));
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

    @Override
    protected void onResume() {
        super.onResume();
        try {
            updateTextView();
        } catch (android.database.CursorIndexOutOfBoundsException e){
            Log.d("catch", "Database still empty");
        }
    }


    private void updateTextView() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    cost = dataSnapshot.child("cost").getValue();
                    purpose = dataSnapshot.child("purpose").getValue();
                    paymentPurpose.setText(purpose.toString());
                    costs.setText(cost.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("catch", "Database still empty.....");
            }
        });


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


    private void setupUIComponents(){
        setContentView(R.layout.activity_overview);
        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.payment);
        button_managePayments = (ImageButton) findViewById(R.id.btn_managePayments);
        paymentPurpose = findViewById(R.id.payment_purpose);
        costs = findViewById(R.id.costs_overview);
        paymentPurpose = findViewById(R.id.payment_purpose);
    }

}