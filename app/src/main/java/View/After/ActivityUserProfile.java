package View.After;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityUserProfile extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        setupNavBar();
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_userprofile);
        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.wg);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void setupNavBar(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.payment:
                        startActivity(new Intent(getApplicationContext(),ActivityOverview.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.wg:
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

    private void handleTopBar(){

    }

}
