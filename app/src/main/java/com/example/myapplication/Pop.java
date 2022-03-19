package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Pop extends Activity {

    EditText text, recipient, subject;
    Button btnSend;
    String userMessage;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    ArrayList<ArrayList<String>> flatContents;
    String[] content;
    String lines;
    final String FIREBASEPATH = "https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        setupWindow();
        initFirebase();
        getFlatIDinFirebase();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDeepLink();
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

    private void setupUIComponents() {
        setContentView(R.layout.popupwindow);
        text = findViewById(R.id.popupwindow_edit_text_message);
        btnSend = findViewById(R.id.popupwindow_btn_send);
        recipient = findViewById(R.id.popupwindow_edit_text_to);
        subject = findViewById(R.id.popupwindow_edit_text_subject);
    }

    private void setupWindow() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(width,(int)(height*.8));
    }

    private void initFirebase(){
        flatContents = new ArrayList<>();
        database = FirebaseDatabase.getInstance(FIREBASEPATH);
        databaseReference = database.getReference("Flats");
        mAuth = FirebaseAuth.getInstance();
    }

    private void getFlatIDinFirebase(){
        readData(new Pop.FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<ArrayList<String>> list) {
            }
        });
    }

    private interface FirebaseCallback {
        void onCallback(ArrayList<ArrayList<String>> list);
    }

    // Get the data from Firebase Server online
    private void readData(Pop.FirebaseCallback firebaseCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    int flatSize = snap.getValue(Flats.class).getFlatSize();
                    ArrayList<String> flatContent = snap.getValue(Flats.class).getData(flatSize);
                    flatContents.add(flatContent);
                }
                // Wait for the server to retrieve the data
                firebaseCallback.onCallback(flatContents);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }

    private void createDeepLink() {
        String currentUserEmail = mAuth.getCurrentUser().getEmail().toString();
        for(int i = 0; i < flatContents.size(); i++){
            if(flatContents.get(i).contains(currentUserEmail)) {
                String currentUserFlat = flatContents.get(i).toString();
                content = currentUserFlat.split(",");
            }
        }

        String firstLine = "Hi,";
        String secondLine = "please join my Flat. Inside the app, enter this flat ID:";
        String thirdLine = extractFlatID();
        String fourthLine = "Click on this link to directly sign in!";
        String fifthLine = "https://wgfinance.page.link/join";
        lines = firstLine + "\n" + secondLine + "\n" + thirdLine + "\n" + fourthLine + "\n" + fifthLine;
        text.setText(lines);
    }

    private String extractFlatID(){
        for(int i = 0 ; i < content.length ; i++){
            String s = content[i];
            s = s.trim();
            Log.d("debuga", s);
            }
        return content[0].substring(1);
    }

}
