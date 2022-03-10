package com.example.myapplication;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class ActivityPaymentOverview extends AppCompatActivity /*implements OnItemSelectedListener*/ {

    public static final String LOG_TAG = ActivityStartScreen.class.getSimpleName();
    Button button_savepayment;
    EditText editTextCost, editTextPurpose;
    String userNameInput;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReferenceFlat;
    DatabaseReference databaseReferenceUser;
    FirebaseDatabase database;
    TextView useremail;
    int flatSize;

    String currentUser;

    List<String> categories = new ArrayList<String>();

    String[] categorieField = {"","","","",""};

    String flatName;

    long maxId;

    int actualCosts;

    //checkable Spinner Items
    TextView selectMate;
    boolean[] selectedMates;
    ArrayList<Integer> matesList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_paymentoverview);
        useremail = findViewById(R.id.show_email);
        database = FirebaseDatabase.getInstance("https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReferenceFlat = database.getReference("Flats");
        databaseReferenceUser = database.getReference("Users");
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
        //assign variable
        selectMate = findViewById(R.id.select_mates);

        //Spinner spinner = (Spinner) findViewById(R.id.spinner_members);

        //spinner.setOnItemSelectedListener(this);

        //TODO: refine code!
        //TODO: show names in spinner instead of email
        currentUser = firebaseAuth.getCurrentUser().getEmail();

        categories.add(currentUser);

        Query checkFlatName = databaseReferenceFlat.orderByChild("firstUser").equalTo(currentUser);

        checkFlatName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                        flatSize = snap.getValue(Flats.class).getFlatSize();
                        System.out.println(flatSize);
                        System.out.println(snap.getValue(Flats.class).getProfileName());


                        switch(flatSize) {
                            case 2:
                                categories.add(snap.getValue(Flats.class).getSecondUser());
                                for(int i = 0; i < categories.size(); i++){
                                    categorieField[i] = categories.get(i);
                                }
                                break;
                            case 3:
                                categories.add(snap.getValue(Flats.class).getSecondUser());
                                categories.add(snap.getValue(Flats.class).getThirdUser());
                                for(int i = 0; i < categories.size(); i++){
                                    categorieField[i] = categories.get(i);
                                }
                                break;
                            case 4:
                                categories.add(snap.getValue(Flats.class).getSecondUser());
                                categories.add(snap.getValue(Flats.class).getThirdUser());
                                categories.add(snap.getValue(Flats.class).getFourthUser());
                                for(int i = 0; i < categories.size(); i++){
                                    categorieField[i] = categories.get(i);
                                }
                                break;
                            case 5:
                                categories.add(snap.getValue(Flats.class).getSecondUser());
                                categories.add(snap.getValue(Flats.class).getThirdUser());
                                categories.add(snap.getValue(Flats.class).getFourthUser());
                                categories.add(snap.getValue(Flats.class).getFifthUser());
                                for(int i = 0; i < categories.size(); i++){
                                    categorieField[i] = categories.get(i);
                                }
                                break;

                        }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query checkFlatName2 = databaseReferenceFlat.orderByChild("secondUser").equalTo(currentUser);

        checkFlatName2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    //if(snap.getValue(Flats.class).getFirstUser().equals(firebaseAuth.getCurrentUser().getEmail())){
                    flatSize = snap.getValue(Flats.class).getFlatSize();
                    System.out.println(flatSize);
                    System.out.println(snap.getValue(Flats.class).getProfileName());


                    switch(flatSize) {
                        case 2:
                            categories.add(snap.getValue(Flats.class).getName());
                            for(int i = 0; i < categories.size(); i++){
                                categorieField[i] = categories.get(i);
                            }
                            break;
                        case 3:
                            categories.add(snap.getValue(Flats.class).getName());
                            categories.add(snap.getValue(Flats.class).getThirdUser());
                            for(int i = 0; i < categories.size(); i++){
                                categorieField[i] = categories.get(i);
                            }
                            break;
                        case 4:
                            categories.add(snap.getValue(Flats.class).getName());
                            categories.add(snap.getValue(Flats.class).getThirdUser());
                            categories.add(snap.getValue(Flats.class).getFourthUser());
                            for(int i = 0; i < categories.size(); i++){
                                categorieField[i] = categories.get(i);
                            }
                            break;
                        case 5:
                            categories.add(snap.getValue(Flats.class).getName());
                            categories.add(snap.getValue(Flats.class).getThirdUser());
                            categories.add(snap.getValue(Flats.class).getFourthUser());
                            categories.add(snap.getValue(Flats.class).getFifthUser());
                            for(int i = 0; i < categories.size(); i++){
                                categorieField[i] = categories.get(i);
                            }
                            break;

                    }
                    //}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query checkFlatName3 = databaseReferenceFlat.orderByChild("thirdUser").equalTo(currentUser);

        checkFlatName3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    //if(snap.getValue(Flats.class).getFirstUser().equals(firebaseAuth.getCurrentUser().getEmail())){
                    flatSize = snap.getValue(Flats.class).getFlatSize();
                    System.out.println(flatSize);
                    System.out.println(snap.getValue(Flats.class).getProfileName());


                    switch(flatSize) {
                        case 3:
                            categories.add(snap.getValue(Flats.class).getName());
                            categories.add(snap.getValue(Flats.class).getSecondUser());
                            for(int i = 0; i < categories.size(); i++){
                                categorieField[i] = categories.get(i);
                            }
                            break;
                        case 4:
                            categories.add(snap.getValue(Flats.class).getName());
                            categories.add(snap.getValue(Flats.class).getSecondUser());
                            categories.add(snap.getValue(Flats.class).getFourthUser());
                            for(int i = 0; i < categories.size(); i++){
                                categorieField[i] = categories.get(i);
                            }
                            break;
                        case 5:
                            categories.add(snap.getValue(Flats.class).getName());
                            categories.add(snap.getValue(Flats.class).getSecondUser());
                            categories.add(snap.getValue(Flats.class).getFourthUser());
                            categories.add(snap.getValue(Flats.class).getFifthUser());
                            for(int i = 0; i < categories.size(); i++){
                                categorieField[i] = categories.get(i);
                            }
                            break;

                    }
                    //}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query checkFlatName4 = databaseReferenceFlat.orderByChild("fourthUser").equalTo(currentUser);

        checkFlatName4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    //if(snap.getValue(Flats.class).getFirstUser().equals(firebaseAuth.getCurrentUser().getEmail())){
                    flatSize = snap.getValue(Flats.class).getFlatSize();
                    System.out.println(flatSize);
                    System.out.println(snap.getValue(Flats.class).getProfileName());


                    switch(flatSize) {
                        case 4:
                            categories.add(snap.getValue(Flats.class).getName());
                            categories.add(snap.getValue(Flats.class).getSecondUser());
                            categories.add(snap.getValue(Flats.class).getThirdUser());
                            for(int i = 0; i < categories.size(); i++){
                                categorieField[i] = categories.get(i);
                            }
                            break;
                        case 5:
                            categories.add(snap.getValue(Flats.class).getName());
                            categories.add(snap.getValue(Flats.class).getSecondUser());
                            categories.add(snap.getValue(Flats.class).getThirdUser());
                            categories.add(snap.getValue(Flats.class).getFifthUser());
                            for(int i = 0; i < categories.size(); i++){
                                categorieField[i] = categories.get(i);
                            }
                            break;

                    }
                    //}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query checkFlatName5 = databaseReferenceFlat.orderByChild("fifthUser").equalTo(currentUser);

        checkFlatName5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    //if(snap.getValue(Flats.class).getFirstUser().equals(firebaseAuth.getCurrentUser().getEmail())){
                    flatSize = snap.getValue(Flats.class).getFlatSize();
                    System.out.println(flatSize);
                    System.out.println(snap.getValue(Flats.class).getProfileName());

                            categories.add(snap.getValue(Flats.class).getName());
                            categories.add(snap.getValue(Flats.class).getSecondUser());
                            categories.add(snap.getValue(Flats.class).getThirdUser());
                            categories.add(snap.getValue(Flats.class).getFourthUser());

                    for(int i = 0; i < categories.size(); i++){
                        categorieField[i] = categories.get(i);
                    }

                    //}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //initialize selected day array
        selectedMates = new boolean[categorieField.length];

        selectMate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        ActivityPaymentOverview.this
                );
                //set title
                builder.setTitle("select your mates");
                //set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(categorieField, selectedMates, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i, boolean isChecked) {
                        //Check condition
                        if(isChecked){
                            //when checkbox selected
                            //add position in List
                            matesList.add(i);
                            //sort list
                            Collections.sort(matesList);
                        }else {
                            //when checkbox unselected
                            //remove position from list
                            matesList.remove(i);
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        //Use for Loop
                        for (int j = 0; j < matesList.size(); j++) {
                            //concat array value
                                stringBuilder.append(categorieField[matesList.get(j)]);
                            //Check codition
                            if (j != matesList.size() -1){
                                //When j value not equal to day list size -1, add comma
                                stringBuilder.append(", ");
                            }
                        }
                        //Set text on text vies
                        selectMate.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //dismiss dialog
                        dialog.dismiss();
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                       //use for loop
                       for(int j = 0; j < selectedMates.length; j++){
                           //remove all selection
                           selectedMates[j] = false;
                           //clear list
                           matesList.clear();
                           //clear text view value
                           selectMate.setText("");

                       }
                    }
                });
                //show dialog
                builder.show();
            }
        });

        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //spinner.setAdapter(dataAdapter);
        //System.out.println("spinner läuft");
    }

    /*@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }*/

    private void retrieveData() {
    }

    private void getHousemateNames(){

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
                String receiverName = shareBill.getText().toString();
                String flat = getFlat();

                if (costString.isEmpty()){Toast.makeText(getApplicationContext(), "EmptyField!",Toast.LENGTH_LONG).show();}
                if (!costString.isEmpty()){
                            actualCosts = Integer.valueOf(costString) / 2;
                }

                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/");
                DatabaseReference myRefPayments = database.getReference("Payments");

                PaymentMemo payment = new PaymentMemo(cost, purpose, useremail, receiverName, flat);

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

    private String getFlat() {
        currentUser = firebaseAuth.getCurrentUser().getEmail();

        Query checkFlat = databaseReferenceFlat.orderByChild("firstUser").equalTo(currentUser);

        checkFlat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    flatName = snap.getValue(Flats.class).getProfileName();
                    System.out.println(flatName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Query checkFlat2 = databaseReferenceFlat.orderByChild("SecondUser").equalTo(currentUser);

        checkFlat2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    flatName = snap.getValue(Flats.class).getProfileName();
                    System.out.println(flatName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Query checkFlat3 = databaseReferenceFlat.orderByChild("ThirdUser").equalTo(currentUser);

        checkFlat3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    flatName = snap.getValue(Flats.class).getProfileName();
                    System.out.println(flatName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Query checkFlat4 = databaseReferenceFlat.orderByChild("ForthUser").equalTo(currentUser);

        checkFlat4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    flatName = snap.getValue(Flats.class).getProfileName();
                    System.out.println(flatName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Query checkFlat5 = databaseReferenceFlat.orderByChild("FifthUser").equalTo(currentUser);

        checkFlat5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    flatName = snap.getValue(Flats.class).getProfileName();
                    System.out.println(flatName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return flatName;
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

