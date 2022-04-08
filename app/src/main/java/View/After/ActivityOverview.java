package View.After;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import Adapter.PaymentAdapter;
import Presenter.Overview.OverviewContract;
import Presenter.Overview.OverviewPresenter;
import Utils.DialogListener;
import Utils.PaymentDialog;
import Utils.RecyclerItemClickListener;

public class ActivityOverview extends AppCompatActivity implements OverviewContract.View, DialogListener {


    FloatingActionButton createNewPayment;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String currentUserEmail;
    OverviewPresenter mOverviewPresenter;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    PaymentAdapter mAdapter;
    ArrayList<ArrayList <String>> payments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        mOverviewPresenter = new OverviewPresenter(this);
        onNewPaymentButtonClicked();
    }

    @Override
    protected void onResume(){
        super.onResume();
        getCurrentUser();
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        mOverviewPresenter.retrievePayment(currentUserEmail);
                    }
                },
                1000);
    }

    private void getCurrentUser(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        currentUserEmail = user.getEmail();
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_overview);
        createNewPayment = findViewById(R.id.btn_managePayments);
        recyclerView = findViewById(R.id.recyclerview_payments);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void onNewPaymentButtonClicked(){
        createNewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("triggered ", "now");
                Intent i = new Intent(ActivityOverview.this, ActivityPaymentOverview.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onPaymentFound(ArrayList<ArrayList <String>> paymentsList) {
        payments = paymentsList;
        Log.d("counter da = ", String.valueOf(payments.size()));
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new PaymentAdapter(this, payments);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onLongItemClick(View view, int position) {
                handleDialog(position);
            }
        }));
    }

    private void handleDialog(int pos){
        View v = recyclerView.getChildAt(pos);
        TextView paymentPurpose = v.findViewById(R.id.payment_purpose);
        TextView paymentCost = v.findViewById(R.id.payment_cost);
        String purpose = paymentPurpose.getText().toString();
        String cost = paymentCost.getText().toString();
        String flatID = payments.get(pos).get(3);
        String paymentID = payments.get(pos).get(4);

        for(int i = 0 ; i < payments.get(pos).size() ; i++){
            Log.d("elemente", String.valueOf(i) + " = " + payments.get(pos).get(i));
        }



        Log.d("gegentest", flatID + "--" + paymentID);
        PaymentDialog paymentDialog = new PaymentDialog();
        Bundle bundle = new Bundle();
        bundle.putString("PAYMENTPURPOSE", purpose);
        bundle.putString("PAYMENTCOST", cost);
        bundle.putString("FLATID", flatID);
        bundle.putString("PAYMENTID", paymentID);
        paymentDialog.setArguments(bundle);
        paymentDialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onPause(){
        super.onPause();
        payments.clear();
    }

    @Override
    public void onPaymentNotFound() {
    }

    @Override
    public void onReturnValue(String id) {
        mOverviewPresenter.deletePayment(id);
        finish();
        overridePendingTransition(0,0);
        startActivity(getIntent());
        overridePendingTransition(0,0);
    }
}