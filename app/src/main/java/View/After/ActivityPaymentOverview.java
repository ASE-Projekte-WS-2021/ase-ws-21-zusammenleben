package View.After;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;

import Entities.Flat;
import Entities.Payment;
import Presenter.PaymentOverview.PaymentOverviewContract;
import Presenter.PaymentOverview.PaymentOverviewPresenter;

public class ActivityPaymentOverview extends AppCompatActivity implements PaymentOverviewContract.View {

    PaymentOverviewPresenter mPaymentOverviewPresenter;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String currentUserEmail;
    String dialogTitle = "Wer soll bezahlen?";

    EditText insertCosts, insertPurpose;
    TextView selectMembers;
    Button btnSavePayment;
    Flat currentFlat;
    int retrievedFlatSize;
    boolean[] retrievedSelectedMembers;
    String[] retrievedMemberNames;
    AlertDialog.Builder builder;
    ArrayList<Integer> memberPositions = new ArrayList<>();
    boolean cameFromDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        mPaymentOverviewPresenter = new PaymentOverviewPresenter(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        getCurrentUser();
        try{
            Bundle extras = getIntent().getExtras();
            cameFromDialog = extras.getBoolean("STATE");
        } catch (NullPointerException e){
            cameFromDialog = false;
        }

        if(cameFromDialog){
            String transmittedPurpose = getIntent().getExtras().getString("PAYMENTPURPOSE");
            String transmittedCost = getIntent().getExtras().getString("PAYMENTCOST");
            insertCosts.setText(transmittedCost);
            insertPurpose.setText(transmittedPurpose);
        }
    }


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
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

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
                //Split String for solo Strings in arrayList (But where?)
                ArrayList<String> insertedReceivers = new ArrayList<>();
                insertedReceivers.add(selectMembers.getText().toString());
                // error handling hier einbauen
                if(cameFromDialog){
                    mPaymentOverviewPresenter.updatePayment(insertedCost, insertedPurpose, insertedReceivers);
                } else {
                    mPaymentOverviewPresenter.savePayment(insertedCost, insertedPurpose, insertedReceivers);
                }
                Intent i = new Intent(ActivityPaymentOverview.this, ActivityOverview.class);
                startActivity(i);
            }
        });
    }

    private void unpackFlatData(){
        retrievedFlatSize = mPaymentOverviewPresenter.retrieveFlatSize(currentFlat);
        retrievedSelectedMembers = mPaymentOverviewPresenter.retrieveSelectedMembers(currentFlat);
        retrievedMemberNames = mPaymentOverviewPresenter.retrieveMemberNames(currentFlat);
    }

    private void buildDialog(){
        selectMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(ActivityPaymentOverview.this);
                builder.setTitle(dialogTitle);
                builder.setCancelable(false);
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

    private void setPositiveButton(){
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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

    private void setNegativeButton(){
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }

    private void setNeutralButton(){
        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
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
    public void onPaymentFound(Payment payment) {
    }
}