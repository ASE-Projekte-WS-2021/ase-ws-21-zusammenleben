package View.After;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;

import Entities.Flat;
import Entities.Payment;
import Presenter.PaymentOverview.PaymentOverviewContract;
import Presenter.PaymentOverview.PaymentOverviewPresenter;
import View.Before.LoginActivity;

public class ActivityPaymentOverview extends AppCompatActivity implements PaymentOverviewContract.View {

    // UI components
    EditText insertCosts, insertPurpose;
    TextView selectMembers;
    Button btnSavePayment;
    MaterialToolbar toolbar;

    // Architectural components
    PaymentOverviewPresenter mPaymentOverviewPresenter;
    FirebaseAuth mAuth;
    FirebaseUser user;

    // Util data
    String currentUserEmail,transmittedPaymentID, transmittedFlatID;
    boolean cameFromDialog;
    private static final String DIALOGTITLE = "Wer soll bezahlen?";
    Flat currentFlat;
    int retrievedFlatSize;

    // Checkbox data
    boolean[] retrievedSelectedMembers;
    String[] retrievedMemberNames;
    AlertDialog.Builder builder;
    ArrayList<Integer> memberPositions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        mPaymentOverviewPresenter = new PaymentOverviewPresenter(this);
        handleTopBar();
    }

    @Override
    protected void onResume(){
        super.onResume();
        getCurrentUser();
        // A bit hacky, but the try-catch enables us to re-use our Activity and MVP classes
        // Edit the payment and create a new one in the same screen
        try{
            Bundle extras = getIntent().getExtras();
            cameFromDialog = extras.getBoolean("STATE");
        } catch (NullPointerException e){
            cameFromDialog = false;
        }

        // Unpack the attached data, of a payment should be edited and not newly created
        if(cameFromDialog){
            String transmittedPurpose = getIntent().getExtras().getString("PAYMENTPURPOSE");
            String transmittedCost = getIntent().getExtras().getString("PAYMENTCOST");
            transmittedFlatID = getIntent().getExtras().getString("FLATID");
            transmittedPaymentID = getIntent().getExtras().getString("PAYMENTID");
            insertCosts.setText(transmittedCost);
            insertPurpose.setText(transmittedPurpose);
        }
    }


    // Retrieve user from Firebase, simple and lightweight
    private void getCurrentUser(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        currentUserEmail = user.getEmail();
        mPaymentOverviewPresenter.retrieveFlat(currentUserEmail);
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_payment_overview);
        btnSavePayment = findViewById(R.id.btn_save_payment);
        insertCosts = findViewById(R.id.insert_costs);
        insertPurpose = findViewById(R.id.insert_purpose);
        selectMembers = findViewById(R.id.select_mates);
        toolbar = findViewById(R.id.topAppBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    // UI method
    private void handleTopBar(){
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.wg_screen:
                        startActivity(new Intent(getApplicationContext(),ActivityUserProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    // The callback data arrived, now the payment can be dealt with
    @Override
    public void onFlatFound(Flat flat) {
        currentFlat = flat;
        unpackFlatData();
        buildDialog();
        btnSavePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double insertedCost = Double.parseDouble(insertCosts.getText().toString());
                String insertedPurpose = insertPurpose.getText().toString();
                ArrayList<String> insertedReceivers = new ArrayList<>();
                insertedReceivers.add(selectMembers.getText().toString());
                // boolean cameFromDialog explained in onResume()
                if(cameFromDialog){
                    mPaymentOverviewPresenter.updatePayment(insertedCost, insertedPurpose, insertedReceivers, transmittedFlatID, transmittedPaymentID);
                } else {
                    mPaymentOverviewPresenter.savePayment(insertedCost, insertedPurpose, insertedReceivers);
                }
            }
        });
    }

    // Retrieve attached data
    private void unpackFlatData(){
        retrievedFlatSize = mPaymentOverviewPresenter.retrieveFlatSize(currentFlat);
        retrievedSelectedMembers = mPaymentOverviewPresenter.retrieveSelectedMembers(currentFlat);
        retrievedMemberNames = mPaymentOverviewPresenter.retrieveMemberNames(currentFlat);
    }

    // Create the checkbox
    private void buildDialog(){
        selectMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // initializing
                builder = new AlertDialog.Builder(ActivityPaymentOverview.this, R.style.AlertDialogStyle);
                builder.setTitle(DIALOGTITLE);
                builder.setCancelable(false);
                // populating
                builder.setMultiChoiceItems(retrievedMemberNames, retrievedSelectedMembers, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                        if(isChecked){
                            memberPositions.add(i);
                            Collections.sort(memberPositions);
                        } else {
                            memberPositions.remove(i);
                        }
                    }
                });
                setPositiveButton();
                setNegativeButton();
                setNeutralButton();
                builder.show();
            }
        });
    }

    // listen for user input
    private void setPositiveButton(){
        builder.setPositiveButton("Ausführen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0 ; j < memberPositions.size() ; j++){
                    stringBuilder.append(retrievedMemberNames[memberPositions.get(j)]);
                    if(j != memberPositions.size() -1){
                        stringBuilder.append(", ");
                    }
                }
                selectMembers.setText(stringBuilder.toString());
            }
        });
    }

    // listen for user input
    private void setNegativeButton(){
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }

    // listen for user input
    private void setNeutralButton(){
        builder.setNeutralButton("Auswahl aufheben", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for (int j = 0 ; j < retrievedSelectedMembers.length ; j++){
                    retrievedSelectedMembers[j] = false;
                    memberPositions.clear();
                    selectMembers.setText("");
                }
            }
        });
    }

    @Override
    public void startIntent() {
        Intent i = new Intent(ActivityPaymentOverview.this, ActivityOverview.class);
        startActivity(i);
    }

    // interface method
    @Override
    public void onPaymentFound(Payment payment) {
    }
}