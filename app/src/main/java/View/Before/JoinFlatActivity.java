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

import Presenter.JoinFlat.JoinFlatContract;
import Presenter.JoinFlat.JoinFlatPresenter;

public class JoinFlatActivity extends AppCompatActivity implements JoinFlatContract.View {

    Button joinFlat, findFlat;
    EditText flatName;
    TextView foundFlatAddress, foundFlatOwner, foundFlatPeople;

    JoinFlatPresenter mJoinFlatPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        mJoinFlatPresenter = new JoinFlatPresenter(this);
        handleSearchButtonClick();
    }

    private void handleSearchButtonClick(){
        findFlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchFlat = flatName.getText().toString();
                mJoinFlatPresenter.retrieveFlat(searchFlat);
                //WG foundWG = mJoinWGPresenter.sendFlat();
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
                mJoinFlatPresenter.addUserToFlat(currentUserEmail, founder);
            }
        });
    }

    @Override
    public void onFlatNotFound(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}

// TODO : address = flat ID -> bring das einheitlich zueinander