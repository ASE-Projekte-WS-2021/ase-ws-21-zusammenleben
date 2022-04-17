package View.Before;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import Presenter.AddFlat.AddFlatContract;
import Presenter.AddFlat.AddFlatPresenter;
import View.After.ActivityOverview;

public class AddFlatActivity extends AppCompatActivity implements AddFlatContract.View, View.OnClickListener {

    // UI components
    Button btnCreateFlat;
    EditText size, flat_name, flatIDText;

    // Architectural
    AddFlatPresenter mAddFlatPresenter;
    FirebaseAuth mAuth;
    FirebaseUser user;

    // Util
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        btnCreateFlat.setOnClickListener(this);
        mAddFlatPresenter = new AddFlatPresenter(this);
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_addflat);
        btnCreateFlat = findViewById(R.id.btn_addFlat);
        flatIDText = findViewById(R.id.flat_profile_name);
        size = findViewById(R.id.size_of_flat);
        //flat_name = findViewById(R.id.flat_share_name);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    public void onClick(View view) {
        getCurrentUser();
        checkInputData();
    }

    // Querying the firebase for current user
    private void getCurrentUser(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        currentUser = user.getEmail();
    }

    //Used to avoid NullPointerException
    int ParseInt(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Integer.parseInt(strNumber);
            } catch (Exception e) {
                return -1;
            }
        }
        return 0;
    }

    // error handling
    private void checkInputData(){
        String id = flatIDText.getText().toString();
        String name = id;
        int flatSize = ParseInt(size.getText().toString());
        ArrayList<String> members = new ArrayList<>();
        members.add(currentUser);

        if(!TextUtils.isEmpty(id) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(size.getText().toString())){
            mAddFlatPresenter.addFlat(this,name, id, members, flatSize);
            Intent i = new Intent(AddFlatActivity.this, ActivityOverview.class);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), "Bitte füllen alle Felder aus!", Toast.LENGTH_SHORT).show();
        }
    }

    // user feedback
    @Override
    public void onAddFlatSuccess(String message) {
        Toast.makeText(getApplicationContext(), "Ihr WG wurde erfolgreich angelegt.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddFlatFailed(String message) {
        Toast.makeText(getApplicationContext(), "Es ist ein Fehler aufgetreten. Bitte versuchen Sie es erneut!", Toast.LENGTH_SHORT).show();
    }

}