package View.After;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import Presenter.Overview.OverviewContract;

public class ActivityOverview extends AppCompatActivity implements OverviewContract.View {


    FloatingActionButton createNewPayment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        onNewPaymentButtonClicked();
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_overview);
        createNewPayment = findViewById(R.id.btn_managePayments);
    }

    private void onNewPaymentButtonClicked(){
        createNewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityOverview.this, ActivityPaymentOverview.class);
                startActivity(i);
            }
        });
    }
}