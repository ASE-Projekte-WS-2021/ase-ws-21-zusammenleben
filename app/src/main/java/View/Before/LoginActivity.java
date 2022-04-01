package View.Before;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import Presenter.Login.LoginContract;
import Presenter.Login.LoginPresenter;
import View.After.ActivityOverview;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginContract.View {

    // UI components
    EditText loginEmail, loginPassword;
    TextView signUp, forgotPassword;
    Button btnLogin;

    // Structural
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initViews();
    }


    private void setupUIComponents(){
        setContentView(R.layout.activity_login);
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        forgotPassword = findViewById(R.id.forgot_password);
        signUp = findViewById(R.id.login_signup);
        btnLogin = findViewById(R.id.btn_login);
    }

    private void initViews(){
        btnLogin.setOnClickListener(this);
        signUp.setOnClickListener(this);
        mLoginPresenter = new LoginPresenter(this);
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_login:
                checkLoginDetails();
                break;
            case R.id.login_signup:
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
        }
    }

    private void checkLoginDetails(){
        if(!TextUtils.isEmpty(loginEmail.getText().toString()) && !TextUtils.isEmpty(loginPassword.getText().toString())){
            initLogin(loginEmail.getText().toString(), loginPassword.getText().toString());
        } else {
            loginEmail.setError("Please enter valid data");
            loginPassword.setError("Please enter valid data");
        }
    }

    private void initLogin(String email, String password){
        mLoginPresenter.login(this, email, password);
    }

    @Override
    public void onLoginSuccess(String message) {
        Log.d("login", "succesful!");
        Intent i = new Intent(LoginActivity.this, ActivityOverview.class);
        startActivity(i);
        Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFailure(String message) {
        Log.d("login", message);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}