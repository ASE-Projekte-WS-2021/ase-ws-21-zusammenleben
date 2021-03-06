package View.Before;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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
        forgotPassword.setOnClickListener(this);
        mLoginPresenter = new LoginPresenter(this);
    }

    // handle user interaction
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_login:
                checkLoginDetails();
                break;
            case R.id.login_signup:
                Intent registration = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(registration);
                break;
            case R.id.forgot_password:
                Intent password = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(password);
        }
    }

    // error handling
    private void checkLoginDetails(){
        if (!Patterns.EMAIL_ADDRESS.matcher(loginEmail.getText().toString()).matches() || TextUtils.isEmpty(loginEmail.getText().toString())){
            loginEmail.setError("Bitte geben Sie ihre korrekte E-Mail an.");
            loginEmail.setFocusable(true);
        } else if (TextUtils.isEmpty(loginPassword.getText().toString())){
            loginPassword.setError("Bitte geben Sie ihr Passwort korrekt an.");
            loginPassword.setFocusable(true);
        } else {
            initLogin(loginEmail.getText().toString(), loginPassword.getText().toString());
        }
    }

    // start mvp transaction
    private void initLogin(String email, String password){
        mLoginPresenter.login(this, email, password);
    }


    // mvp transaction complete
    @Override
    public void onLoginSuccess(String message) {
        Intent i = new Intent(LoginActivity.this, ActivityOverview.class);
        startActivity(i);
    }

    @Override
    public void onLoginFailure(String message) {
        Toast.makeText(getApplicationContext(), "Ung??ltige Anmeldeinformation.Bitte versuchen Sie es erneut!", Toast.LENGTH_SHORT).show();
    }
}