package View.Before;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText emailforpassword;
    Button resetpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_forgotpassword);
        emailforpassword = findViewById(R.id.forgot_password_email);
        resetpassword = findViewById(R.id.btn_resetPassword);
    }

}
