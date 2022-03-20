package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class ActivityPaymentOverview extends AppCompatActivity {

    Button button_savepayment;
    EditText editTextCost, editTextPurpose;
    TextView useremail;
    TextView selectMate;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReferenceFlat;
    DatabaseReference databaseReferenceUser;
    DatabaseReference databaseReferencePayment;
    FirebaseDatabase database;

    int flatSize;
    int actualCosts;
    long paymentCounter;

    String currentUser;
    String flatID;

    List<String> categories = new ArrayList<String>();
    String[] categorieField;

    //checkable Spinner Items
    boolean[] selectedMates;
    ArrayList<Integer> matesList = new ArrayList<>();
    ArrayList<ArrayList<String>> flatContents = new ArrayList<>();
    String[] content;

    ArrayList<ArrayList<String>> paymentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        initFirebase();
        getFlatIDinFirebase();
        savePayment();
    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }

    private void setupUIComponents() {
        setContentView(R.layout.activity_paymentoverview);
        useremail = findViewById(R.id.show_email);
        button_savepayment = (Button) findViewById(R.id.btn_save_payment);
        editTextCost = (EditText) findViewById(R.id.insert_costs);
        editTextPurpose = (EditText) findViewById(R.id.insert_purpose);
        selectMate = findViewById(R.id.select_mates);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //utilSpinner();
    }

    private void initFirebase(){
        database = FirebaseDatabase.getInstance("https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReferenceFlat = database.getReference("Flats");
        databaseReferenceUser = database.getReference("Users");
        databaseReferencePayment = database.getReference("Payments");
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getEmail();
    }

    private void utilSpinner(){
        //initialize selected day array
        categorieField = new String[flatSize];
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
                            //Check condition
                            if (j != matesList.size() -1){
                                //When j value not equal to day list size -1, add comma
                                stringBuilder.append(", ");
                            }
                        }
                        //Set text on text views
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
    }

    private void savePayment() {
        button_savepayment = findViewById(R.id.btn_save_payment);
        final EditText editTextCost = findViewById(R.id.insert_costs);
        final EditText editTextPurpose = findViewById(R.id.insert_purpose);
        final TextView editTextMail = findViewById(R.id.show_email);

        button_savepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFlatID();
                String costString = editTextCost.getText().toString();
                double cost = Double.valueOf(costString);
                String purpose = editTextPurpose.getText().toString();
                String useremail = editTextMail.getText().toString();
                String receiverName = selectMate.getText().toString();
                String flat = flatID;


                if (costString.isEmpty()){Toast.makeText(getApplicationContext(), "EmptyField!",Toast.LENGTH_LONG).show();}
                if (!costString.isEmpty()){
                            actualCosts = (int) (Double.valueOf(costString) / 2);
                }

                PaymentMemo payment = new PaymentMemo(cost, purpose, useremail, receiverName, flat);
                Log.d("paymentcounter", String.valueOf(paymentCounter));
                readDataFromPayments(new FirebaseCallback() {
                    @Override
                    public void onCallback(ArrayList<ArrayList<String>> list) {
                        Log.d("Hi", list.toString());
                        Log.d("Hi", String.valueOf(paymentCounter));
                        String paymentTitle = flatID + String.valueOf(paymentCounter++);
                        databaseReferencePayment.child(paymentTitle).setValue(payment);
                    }
                });

                Toast.makeText(getApplicationContext(), "Successful!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), ActivityOverview.class);
                startActivity(intent);
                }
        });
    }

    private void readDataFromPayments(FirebaseCallback firebaseCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    paymentCounter = snapshot.getChildrenCount();
                    ArrayList<String> payment = ds.getValue(PaymentMemo.class).getData();
                    if(payment.contains(flatID)){
                        paymentList.add(payment);
                    }
                }

                firebaseCallback.onCallback(paymentList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        databaseReferencePayment.addListenerForSingleValueEvent(valueEventListener);
    }

    private interface FirebasePaymentCallback {
        void onCallback(List<String> list);
    }

    private void getFlatIDinFirebase(){
        readData(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<ArrayList<String>> list) {
            }
        });
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d("xxxxx", "onRestart() active!");
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
                getUsers();
                utilSpinner();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        databaseReferenceFlat.addListenerForSingleValueEvent(valueEventListener);
    }


    private void getUsers(){
        getFlatID();

        Query checkFlatName = databaseReferenceFlat.orderByChild("flatID").equalTo(flatID);
        checkFlatName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    flatSize = snap.getValue(Flats.class).getFlatSize();

                    switch(flatSize) {
                        case 2:
                            categories.add(snap.getValue(Flats.class).getFirstUser());
                            categories.add(snap.getValue(Flats.class).getSecondUser());
                            for(int i = 0; i < categories.size(); i++){
                                categorieField[i] = categories.get(i);
                            }
                            break;
                        case 3:
                            categories.add(snap.getValue(Flats.class).getFirstUser());
                            categories.add(snap.getValue(Flats.class).getSecondUser());
                            categories.add(snap.getValue(Flats.class).getThirdUser());
                            for(int i = 0; i < categories.size(); i++){
                                categorieField[i] = categories.get(i);
                            }
                            break;
                        case 4:
                            categories.add(snap.getValue(Flats.class).getFirstUser());
                            categories.add(snap.getValue(Flats.class).getSecondUser());
                            categories.add(snap.getValue(Flats.class).getThirdUser());
                            categories.add(snap.getValue(Flats.class).getFourthUser());
                            for(int i = 0; i < categories.size(); i++){
                                categorieField[i] = categories.get(i);
                            }
                            break;
                        case 5:
                            categories.add(snap.getValue(Flats.class).getFirstUser());
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
    }

    private void getFlatID() {
        for(int i = 0; i < flatContents.size(); i++){
            if(flatContents.get(i).contains(currentUser)) {
                String currentUserFlat = flatContents.get(i).toString();
                content = currentUserFlat.split(",");
            }
        }
        flatID = extractFlatID();
        flatSize = extractFlatSize();
    }

    private String extractFlatID(){
        for(int i = 0 ; i < content.length ; i++){
            String s = content[i];
            s = s.trim();
            Log.d("debug", s);
        }
        return content[0].substring(1);
    }

    private int extractFlatSize(){
        for(int i = 0; i < content.length; i++) {
            String s = content[i];
            s = s.trim();
        }
        System.out.println(content[1].substring(1));
        return Integer.parseInt(content[1].substring(1));
    }

    private void checkUserStatus (){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            useremail.setText(user.getEmail());
        } else {
            finish();
        }
    }
}

