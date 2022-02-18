package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;


public class ActivityPaymentOverview extends AppCompatActivity {

    // For testing the database
    public static final String LOG_TAG = ActivityStartScreen.class.getSimpleName();
    Button button_savepayment;
    EditText editTextCost, editTextPurpose;
    FirebaseAuth firebaseAuth;
    TextView useremail;
    long maxId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //try to setup and test my stuff
        setContentView(R.layout.activity_paymentoverview);
        useremail = findViewById(R.id.show_email);
        firebaseAuth = FirebaseAuth.getInstance();

        Log.d(LOG_TAG, "Opening Datasource.");

        button_savepayment = (Button) findViewById(R.id.btn_save_payment);
        editTextCost = (EditText) findViewById(R.id.insert_costs);
        editTextPurpose = (EditText) findViewById(R.id.insert_purpose);

        savePayment();

    }



    @Override
    protected void onPause() {
        super.onPause();
    }

private void savePayment() {
        button_savepayment = (Button) findViewById(R.id.btn_save_payment);
        final EditText editTextCost = (EditText) findViewById(R.id.insert_costs);
        final EditText editTextPurpose = (EditText) findViewById(R.id.insert_purpose);
        final TextView editTextMail = (TextView) findViewById(R.id.show_email);
        button_savepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String costString = editTextCost.getText().toString();
                double cost = Double.valueOf(costString);
                String purpose = editTextPurpose.getText().toString();
                String useremail = editTextMail.getText().toString();
                TextView shareBill = (TextView)findViewById(R.id.share_your_bill);
                shareBill.setText(costString + purpose);
                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("Payments");
                PaymentMemo payment = new PaymentMemo(cost, purpose, useremail);

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                            maxId = (snapshot.getChildrenCount());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                String paymentCounter = "P" + String.valueOf(maxId+1);
                myRef.child(paymentCounter).setValue(payment);
                Toast.makeText(getApplicationContext(), "Successfull!",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), ActivityOverview.class);
                startActivity(intent);
                }


        });
    }

    private void checkUserStatus (){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            useremail.setText(user.getEmail());
        } else {
            finish();
        }
    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed(); //return to previous screen/activity
        return super.onSupportNavigateUp();
    }
}

