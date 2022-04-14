package View.Before;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseUser;

import Presenter.Registration.RegistrationContract;
import Presenter.Registration.RegistrationPresenter;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, RegistrationContract.View {

    // UI Components
    EditText signUpEmail, signUpPassword, signUpName;
    Button btnCreateAccount;

    // structural
    private RegistrationPresenter mRegistrationPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        initViews();
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_registration);
        signUpEmail = findViewById(R.id.signup_e_mail);
        signUpPassword = findViewById(R.id.signup_password);
        signUpName = findViewById(R.id.signup_name);
        btnCreateAccount = findViewById(R.id.btn_createAccount);
    }

    private void initViews(){
        btnCreateAccount.setOnClickListener(this);
        mRegistrationPresenter = new RegistrationPresenter(this);
    }

    public void onClick(View view){
        checkRegistrationDetails();
    }

    private void checkRegistrationDetails(){

        if (!Patterns.EMAIL_ADDRESS.matcher(signUpEmail.getText().toString()).matches() || TextUtils.isEmpty(signUpEmail.getText().toString())) {
            signUpEmail.setError("Bitte geben Sie ihre korrekte E-Mail an.");
            signUpEmail.setFocusable(true);
        } else if (TextUtils.isEmpty(signUpName.getText().toString())){
            signUpName.setError("Bitte geben Sie ihren Name an.");
            signUpName.setFocusable(true);
        } else if (TextUtils.isEmpty(signUpPassword.getText().toString()) || signUpPassword.getText().toString().length()<6){
            signUpPassword.setError("Bitte geben Sie ihr Passwort mit mehr als 6 Zeichen  an.");
            signUpPassword.setFocusable(true);
        } else {
            initLogin(signUpEmail.getText().toString(), signUpPassword.getText().toString(), signUpName.getText().toString());
        }

        /*if(!TextUtils.isEmpty(signUpEmail.getText().toString()) && !TextUtils.isEmpty(signUpPassword.getText().toString())){
          initLogin(signUpEmail.getText().toString(), signUpPassword.getText().toString(), signUpName.getText().toString());
        } else {
            signUpEmail.setError("Bitte geben Sie ihre E-Mail an.");
            signUpEmail.setFocusable(true);
            signUpPassword.setError("Bitte geben Sie ihr Passwort an.");
            signUpPassword.setFocusable(true);
        }*/
    }

    private void initLogin(String email, String password, String name){
        mRegistrationPresenter.register(this, email, password, name);
        //Intent i = new Intent(RegistrationActivity.this, CreateFlatActivity.class);
        //startActivity(i);
    }

    @Override
    public void onRegistrationSuccess(FirebaseUser firebaseUser) {
        Toast.makeText(getApplicationContext(), "Ihr Konto wurde erfolgreich erstellt", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(RegistrationActivity.this, CreateFlatActivity.class);
        startActivity(i);
    }

    @Override
    public void onRegistrationFailure(String message) {
        Log.d("fail", "fail");
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}