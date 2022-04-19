package View.Before;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import Presenter.ForgotPassword.ForgotPasswordContract;
import Presenter.ForgotPassword.ForgotPasswordPresenter;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener, ForgotPasswordContract.View {

    // UI components
    EditText emailforpassword;
    Button resetpassword;

    // Architectural
    private ForgotPasswordPresenter mPasswordPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        initViews();
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_forgotpassword);
        emailforpassword = findViewById(R.id.forgot_password_email);
        resetpassword = findViewById(R.id.btn_resetPassword);
    }

    private void initViews(){
        resetpassword.setOnClickListener(this);
        mPasswordPresenter = new ForgotPasswordPresenter(this);
    }

    private void initPassReset(String email){
        mPasswordPresenter.passReset(this, email);
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_resetPassword:
                checkPassReset();
                return;
        }
    }

    private void checkPassReset(){
        if(!TextUtils.isEmpty(emailforpassword.getText().toString())){
            initPassReset(emailforpassword.getText().toString());
        } else {
            emailforpassword.setError("Bitte geben Sie eine gültige E-Mail an.");
            emailforpassword.setFocusable(true);
        }
    }

    @Override
    public void onPassResetSuccess(String message) {
        Toast.makeText(getApplicationContext(), "Bitte überprüfen Sie ihre Mails!", Toast.LENGTH_LONG).show();
        Intent login = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        startActivity(login);
    }

    @Override
    public void onPassResetFailure(String message) {
        Toast.makeText(getApplicationContext(), "Diese E-Mail ist nicht korrekt! Bitte versuchen Sie es erneut.", Toast.LENGTH_SHORT).show();
    }

}
