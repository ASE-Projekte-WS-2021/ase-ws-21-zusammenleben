package View.Before;

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

import Presenter.AddWG.AddWGContract;
import Presenter.AddWG.AddWGPresenter;

public class AddWGActivity extends AppCompatActivity implements AddWGContract.View, View.OnClickListener {

    Button btnCreateFlat;
    EditText size, flat_name, flatIDText;
    AddWGPresenter mAddWGPresenter;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        mAddWGPresenter = new AddWGPresenter(this);
        btnCreateFlat.setOnClickListener(this);
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_addwg);
        btnCreateFlat = findViewById(R.id.btn_addwg);
        flatIDText = findViewById(R.id.wg_profile_name);
        size = findViewById(R.id.size_of_flat);
        flat_name = findViewById(R.id.flat_share_name);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    public void onClick(View view) {
        getCurrentUser();
        checkInputData();
    }

    private void getCurrentUser(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        currentUser = user.getEmail();
    }

    private void checkInputData(){
        String id = flatIDText.getText().toString();
        String address = flat_name.getText().toString();
        int flatSize = Integer.valueOf(size.getText().toString());
        ArrayList<String> members = new ArrayList<>();
        members.add(currentUser);

        if(!TextUtils.isEmpty(id) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(size.getText().toString())){
            mAddWGPresenter.addWG(this, address, id, members, flatSize);
        } else {
            Toast.makeText(getApplicationContext(), "Your input was invalid", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAddWGSuccess() {
    }

    @Override
    public void onAddWGFailed() {
    }

}