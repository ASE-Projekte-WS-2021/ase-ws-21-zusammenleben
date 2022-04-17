package View.After;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import Presenter.PopInvite.PopInviteContract;
import Presenter.PopInvite.PopInvitePresenter;
import View.After.ActivityPopInvite;

public class ActivityPopInvite extends AppCompatActivity implements PopInviteContract.Listener, PopInviteContract.View {

    EditText text, recipient, subject;
    Button btnSend;
    String userEmail, userMessage, lines;
    String[] content;
    PopInvitePresenter popInvitePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getCurrentUserEmail();
        setupUIComponents();
        popInvitePresenter = new PopInvitePresenter(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        popInvitePresenter.getFlatID(userEmail);
    }

    private void getCurrentUserEmail(){
        userEmail = getIntent().getExtras().getString("EMAIL");
    }

    private void setupUIComponents(){
        setContentView(R.layout.popupwindow);
        recipient = findViewById(R.id.popupwindow_edit_text_to);
        subject = findViewById(R.id.popupwindow_edit_text_subject);
        text = findViewById(R.id.popupwindow_edit_text_message);
        btnSend = findViewById(R.id.popupwindow_btn_send);
    }

    @Override
    public void onFlatIDRetrieved(String id) {
        String firstLine = "  Hi,";
        String secondLine = "  please join my Flat. Inside the app, enter:";
        String thirdLine = "  " + id;
        String fourthLine = "  Click on this link to directly sign in!";
        String fifthLine = "  https://wgfinance.page.link/join";
        lines = firstLine + "\n" + secondLine + "\n" + thirdLine + "\n" + fourthLine + "\n" + fifthLine;
        text.setText(lines);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
    }

    private void sendMail(){
        String recipientList = recipient.getText().toString();
        String[] recipients = recipientList.split(",");
        String subjectText = subject.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subjectText);
        intent.putExtra(Intent.EXTRA_TEXT, lines);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }
}
