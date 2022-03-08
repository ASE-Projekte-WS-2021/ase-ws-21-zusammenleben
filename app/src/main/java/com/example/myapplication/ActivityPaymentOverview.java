package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

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

import java.util.ArrayList;
import java.util.List;


public class ActivityPaymentOverview extends AppCompatActivity implements OnItemSelectedListener {

    // For testing the database
    public static final String LOG_TAG = ActivityStartScreen.class.getSimpleName();
    Button button_savepayment;
    EditText editTextCost, editTextPurpose;
    FirebaseAuth firebaseAuth;
    TextView useremail;

    long maxId;

    int actualCosts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_paymentoverview);
        useremail = findViewById(R.id.show_email);
        firebaseAuth = FirebaseAuth.getInstance();

        Log.d(LOG_TAG, "Opening Datasource.");

        button_savepayment = (Button) findViewById(R.id.btn_save_payment);
        editTextCost = (EditText) findViewById(R.id.insert_costs);
        editTextPurpose = (EditText) findViewById(R.id.insert_purpose);

        utilSpinner();
        savePayment();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void utilSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner_members);

        spinner.setOnItemSelectedListener(this);
        // TODO lesender Zugriff auf DB um Mitglieder der WG herauszufinden und dann in die jeweilige spinner position zu bringen
        /*FirebaseDatabase database = FirebaseDatabase.getInstance("https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRefPayments = database.getReference("Members");

        PaymentMemo payment = new PaymentMemo(cost, purpose, useremail);

        myRefPayments.addValueEventListener(new ValueEventListener() {
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
        myRefPayments.child(paymentCounter).setValue(payment);

        myRefPayments.push().setValue(payment);*/



        List<String> categories = new ArrayList<String>();
        
        //for (int i = 0; i< categories.length; i++)
        categories.add("username1");
        categories.add("username2");
        categories.add("username3");
        categories.add("username4");
        categories.add("username5");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
        System.out.println("spinner läuft");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
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
                shareBill.setText(costString +" "+ purpose);

                if (costString.isEmpty()){Toast.makeText(getApplicationContext(), "EmptyField!",Toast.LENGTH_LONG).show();}
                if (!costString.isEmpty()){
                            actualCosts = Integer.valueOf(costString) / 2;
                }

                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/");
                DatabaseReference myRefPayments = database.getReference("Payments");

                PaymentMemo payment = new PaymentMemo(cost, purpose, useremail);

                myRefPayments.addValueEventListener(new ValueEventListener() {
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
                myRefPayments.child(paymentCounter).setValue(payment);
                myRefPayments.push().setValue(payment);


                System.out.println("Successfull!");
                System.out.println(actualCosts);

                Toast.makeText(getApplicationContext(), "Successfull!",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), ActivityOverview.class);
                //intent.putExtra("MY Data Key", (Parcelable) payment);
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

