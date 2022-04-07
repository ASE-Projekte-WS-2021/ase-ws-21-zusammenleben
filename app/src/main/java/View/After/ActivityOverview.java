package View.After;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

public class ActivityOverview extends AppCompatActivity implements OverviewContract.View {


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
        mOverviewPresenter.retrievePayment(currentUserEmail);
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
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new PaymentAdapter(this, payments);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onPause(){
        super.onPause();
        payments.clear();
    }

    @Override
    public void onPaymentNotFound() {

    }
}