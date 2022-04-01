package View.Before;

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

import Presenter.JoinWG.JoinWGContract;
import Presenter.JoinWG.JoinWGPresenter;

public class JoinWGActivity extends AppCompatActivity implements JoinWGContract.View {

    Button joinWG, findWG;
    EditText flatName;
    TextView foundFlatAddress, foundFlatOwner, foundFlatPeople;

    JoinWGPresenter mJoinWGPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        mJoinWGPresenter = new JoinWGPresenter(this);
        handleSearchButtonClick();
    }

    private void handleSearchButtonClick(){
        findWG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchFlat = flatName.getText().toString();
                mJoinWGPresenter.retrieveFlat(searchFlat);
                //WG foundWG = mJoinWGPresenter.sendFlat();
            }
        });
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_join_flat);
        joinWG = findViewById(R.id.btn_joinwg);
        flatName = findViewById(R.id.flat_name);
        findWG = findViewById(R.id.btn_findwg);
        foundFlatAddress = findViewById(R.id.found_flat_address);
        foundFlatOwner = findViewById(R.id.found_flat_owner);
        foundFlatPeople = findViewById(R.id.found_flat_people);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }


    @Override
    public void onWGFound(String founder, String id, int size) {
        foundFlatAddress.setText(founder);
        foundFlatOwner.setText(id);
        foundFlatPeople.setText(String.valueOf(size));
        joinWG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                String currentUserEmail = currentUser.getEmail();
                mJoinWGPresenter.addUserToFlat(currentUserEmail, founder);
            }
        });
    }

    @Override
    public void onWGNotFound(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}

// TODO : address = flat ID -> bring das einheitlich zueinander