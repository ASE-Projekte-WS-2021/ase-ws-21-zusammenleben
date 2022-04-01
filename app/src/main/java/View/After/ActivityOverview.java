package View.After;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.wgfinance.R;

import Presenter.Overview.OverviewContract;

public class ActivityOverview extends AppCompatActivity implements OverviewContract.View {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_overview);
    }
}