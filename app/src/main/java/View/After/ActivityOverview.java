package View.After;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
import View.Before.LoginActivity;

public class ActivityOverview extends AppCompatActivity implements OverviewContract.View, DialogListener {

    // UI components
    FloatingActionButton createNewPayment;
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    PaymentAdapter mAdapter;
    MaterialToolbar toolbar;

    // Architectural
    FirebaseAuth mAuth;
    FirebaseUser user;
    OverviewPresenter mOverviewPresenter;

    // Util
    String currentUserEmail;
    ArrayList<ArrayList <String>> payments;
    TextView debtNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        mOverviewPresenter = new OverviewPresenter(this);
        onNewPaymentButtonClicked();
        setupNavBar();
        handleTopBar();
    }

    // mvp transaction inside onResume so it is updated each time the screen is present
    // (after onCreate, onStart, onRestart, etc.)
    @Override
    protected void onResume(){
        super.onResume();
        getCurrentUser();
        // mvp transaction begins
        mOverviewPresenter.retrievePayment(currentUserEmail);
    }

    // retrieve current user by querying firebase
    private void getCurrentUser(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        currentUserEmail = user.getEmail();
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_overview);
        createNewPayment = findViewById(R.id.btn_managePayments);
        recyclerView = findViewById(R.id.recyclerview_payments);
        debtNumber = findViewById(R.id.debtNumber);
        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.payment);
        toolbar = findViewById(R.id.topAppBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    // User interaction
    private void onNewPaymentButtonClicked(){
        createNewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityOverview.this, ActivityPaymentOverview.class);
                startActivity(i);
            }
        });
    }

    // UI method
    private void setupNavBar(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.payment:
                        return true;
                    case R.id.wg:
                        startActivity(new Intent(getApplicationContext(),ActivityUserProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.shopping:
                        startActivity(new Intent(getApplicationContext(),ActivityBasketList.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
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
    }

    // Callback done
    @Override
    public void onPaymentFound(ArrayList<ArrayList <String>> paymentsList, double debt) {
        // init recyclerview and textview for debt
        payments = paymentsList;
        debtNumber.setText(String.valueOf(debt));
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new PaymentAdapter(this, payments);
        recyclerView.setAdapter(mAdapter);
        // user interaction inside recyclerview
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
        // retrieve data from clicked item
        View v = recyclerView.getChildAt(pos);
        TextView paymentPurpose = v.findViewById(R.id.payment_purpose);
        TextView paymentCost = v.findViewById(R.id.payment_cost);
        String purpose = paymentPurpose.getText().toString();
        String cost = paymentCost.getText().toString();
        String flatID = payments.get(pos).get(3);
        String paymentID = payments.get(pos).get(4);

        // attach data to dialog with Bundle
        PaymentDialog paymentDialog = new PaymentDialog();
        Bundle bundle = new Bundle();
        bundle.putString("PAYMENTPURPOSE", purpose);
        bundle.putString("PAYMENTCOST", cost);
        bundle.putString("FLATID", flatID);
        bundle.putString("PAYMENTID", paymentID);

        // activate dialog
        paymentDialog.setArguments(bundle);
        paymentDialog.show(getSupportFragmentManager(), "dialog");
    }

    // clearing the list that populates the recyclerview to avoid duplicate items
    @Override
    public void onPause(){
        super.onPause();
        payments.clear();
    }

    // Starting mvp transaction after user input in dialog
    @Override
    public void onReturnValue(String id) {
        mOverviewPresenter.deletePayment(id);
        // ui handling
        finish();
        overridePendingTransition(0,0);
        startActivity(getIntent());
        overridePendingTransition(0,0);
    }
}