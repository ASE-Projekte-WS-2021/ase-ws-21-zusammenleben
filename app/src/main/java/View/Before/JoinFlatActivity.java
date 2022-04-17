package View.Before;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import Presenter.JoinFlat.JoinFlatContract;
import Presenter.JoinFlat.JoinFlatPresenter;
import View.After.ActivityOverview;

public class JoinFlatActivity extends AppCompatActivity implements JoinFlatContract.View {

    // UI components
    Button joinFlat, findFlat;
    EditText flatName;
    TextView foundFlatAddress, foundFlatOwner, foundFlatPeople;

    // Architectural
    JoinFlatPresenter mJoinFlatPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        mJoinFlatPresenter = new JoinFlatPresenter(this);
        handleSearchButtonClick();
    }

    // user interaction
    private void handleSearchButtonClick(){
        findFlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchFlat = flatName.getText().toString();
                // start mvp transaction
                mJoinFlatPresenter.retrieveFlat(searchFlat);
            }
        });
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_join_flat);
        joinFlat = findViewById(R.id.btn_joinFlat);
        flatName = findViewById(R.id.flat_name);
        findFlat = findViewById(R.id.btn_findFlat);
        foundFlatAddress = findViewById(R.id.found_flat_address);
        foundFlatOwner = findViewById(R.id.found_flat_owner);
        foundFlatPeople = findViewById(R.id.found_flat_people);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }


    // Callback data arrived
    @Override
    public void onFlatFound(String founder, String id, int size) {
        foundFlatAddress.setText(founder);
        foundFlatOwner.setText(id);
        foundFlatPeople.setText(String.valueOf(size));
        joinFlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                String currentUserEmail = currentUser.getEmail();
                // start another mvp transaction
                mJoinFlatPresenter.addUserToFlat(currentUserEmail, founder);
                Intent i = new Intent(JoinFlatActivity.this, ActivityOverview.class);
                startActivity(i);
            }
        });
    }

    // Error handling
    @Override
    public void onFlatNotFound(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}