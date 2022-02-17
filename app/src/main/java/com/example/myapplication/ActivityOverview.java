package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ActivityOverview extends AppCompatActivity {

    ImageButton button_managePayments;
    PaymentMemo payment;
    //TextView testtext;
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;
    String userEmail;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference("Payments/");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //try to setup and test my stuff
        setContentView(R.layout.activity_overview);
        addListenerOnButton();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userEmail = user.getEmail();
        //getData();

        //displaytext();

        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.payment);

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

    @Override
    protected void onResume() {
        super.onResume();
        //PaymentMemoDataSource db = new PaymentMemoDataSource(this);
        try {
            //payment = db.getPaymentMemo();
            updateTextView();
        } catch (android.database.CursorIndexOutOfBoundsException e){
            System.out.println("Database still empty...");
        }
    }

    private void updateTextView() {
        /*System.out.println("hier " +payment);
        String s = payment.toString();
        System.out.println(s);
        String[] substring = s.split("#");
        String cost = substring[0];
        String product = substring[1];
        System.out.println(cost);
        System.out.println(product);
        TextView description = findViewById(R.id.payment_purpose);
        TextView costs = findViewById(R.id.costs);
        description.setText(product);
        costs.setText(cost);*/
    }

    //https://firebase.google.com/docs/database/android/read-and-write?authuser=0
    private void getData() {
        // [START post_value_event_listener]
        myRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                PaymentMemo payment = dataSnapshot.getValue(PaymentMemo.class);
                System.out.println(payment);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ActivityOverview.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }

            //mPostReference.addValueEventListener(paymentListener);
            // [END post_value_event_listener]
        });
    }

    public void addListenerOnButton() {

        //final Context context = this;

        button_managePayments = (ImageButton) findViewById(R.id.btn_managePayments);

        button_managePayments.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //ActivityOverview.this.startActivity(new Intent(ActivityOverview.this, ActivityPaymentOverview.class));
                Intent intent = new Intent(getApplicationContext(), ActivityPaymentOverview.class);
                startActivity(intent);

            }

        });

    }

/*
    public void displaytext(){
        ActivityPaymentOverview inst = new ActivityPaymentOverview();
        testtext = (TextView) findViewById(R.id.last_Payments);
        testtext.setText(inst.getInputCosts()+":"+inst.getInputPurpose());
    }
*/
}
