package View.After;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import Entities.Flat;
import Entities.Payment;
import Presenter.PaymentOverview.PaymentOverviewContract;
import Presenter.PaymentOverview.PaymentOverviewPresenter;

public class ActivityPaymentOverview extends AppCompatActivity implements PaymentOverviewContract.View {

    PaymentOverviewPresenter mPaymentOverviewPresenter;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String currentUserEmail;

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
    }

    private void getCurrentUser(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        currentUserEmail = user.getEmail();
        mPaymentOverviewPresenter.retrieveFlat(currentUserEmail);
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_payment_overview);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    public void onFlatFound(Flat flat) {
        List<String> members = flat.getMembers();
        Log.d("arrived in Activity ", members.toString());
    }

    @Override
    public void onPaymentFound(Payment payment) {

    }
}