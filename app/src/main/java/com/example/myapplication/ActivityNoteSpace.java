package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class ActivityNoteSpace extends AppCompatActivity{

    private EditText setTitle, setSubtitle, setNotice;
    private TextView dateandtime;
    ImageView imageViewback;
    ImageView imageViewsave;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    private ImageView imageAddUrl;
    private TextView textWebUrl;
    private LinearLayout layoutWebUrl;
    private AlertDialog dialogAddUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        initFirebase();
        initMenu();
        saveNote();
        setImageViewback();
        setDateandtime();
    }

    private void initMenu(){
        imageAddUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddURLDialog();
            }
        });
    }

    private void fillandsaveNote(){

        String title = setTitle.getText().toString().trim();
        String subtitle = setSubtitle.getText().toString().trim();
        String notice= setNotice.getText().toString().trim();
        String url = textWebUrl.getText().toString().trim();

        if (title.isEmpty()){
            Toast.makeText(this, "A title and a subtitle are required!", Toast.LENGTH_SHORT).show();
            return;
        }else if (subtitle.isEmpty()){
            Toast.makeText(this, "A title and a subtitle are required!", Toast.LENGTH_SHORT).show();
            return;
        }else {
            DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("mynotes").document();
            Map<String, Object> note= new HashMap<>();
            note.put("title", title);
            note.put("subtitle", subtitle);
            note.put("notice", notice);
            note.put("url", url);

            if(layoutWebUrl.getVisibility() == View.VISIBLE){
                note.put("url", url);
            }

            documentReference.set(note).addOnSuccessListener(unused -> {
                Toast.makeText(getApplicationContext(), "Note successfully created!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ActivityNoteSpace.this,ActivityStartScreen.class));
            }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show());
        }

    }

    private void showAddURLDialog(){
        if(dialogAddUrl == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityNoteSpace.this);
            View view = LayoutInflater.from(this).inflate(
                    R.layout.layout_add_url, (ViewGroup) findViewById(R.id.layoutAddUrlContainer)
            );
            builder.setView(view);

            dialogAddUrl = builder.create();
            if(dialogAddUrl.getWindow() != null){
                dialogAddUrl.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }

            final EditText inputUrl= view.findViewById(R.id.inputURL);
            inputUrl.requestFocus();

            view.findViewById(R.id.textAdd).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(inputUrl.getText().toString().trim().isEmpty()){
                        Toast.makeText(ActivityNoteSpace.this, "Enter URL", Toast.LENGTH_SHORT).show();
                    } else if (!Patterns.WEB_URL.matcher(inputUrl.getText().toString()).matches()){
                        Toast.makeText(ActivityNoteSpace.this, "Enter correct URL", Toast.LENGTH_SHORT).show();
                    } else {
                        textWebUrl.setText(inputUrl.getText().toString());
                        layoutWebUrl.setVisibility(View.VISIBLE);
                        dialogAddUrl.dismiss();
                    }
                }
            });

            view.findViewById(R.id.textCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogAddUrl.dismiss();
                }
            });

        }
        dialogAddUrl.show();
    }

    public void setDateandtime(){
        dateandtime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())
        );
    }

    public void saveNote(){
        imageViewsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillandsaveNote();
            }
        });
    }

    public void setImageViewback(){
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_notespace);
        setTitle = findViewById(R.id.inputNoteTitle);
        setSubtitle = findViewById(R.id.noteSubtitle);
        setNotice = findViewById(R.id.inputNote);
        dateandtime = findViewById(R.id.textDateTime);
        textWebUrl = findViewById(R.id.textWebURL);
        layoutWebUrl = findViewById(R.id.layoutWebUrl);
        imageAddUrl = findViewById(R.id.imageAddWebLink);
        imageViewsave = findViewById(R.id.imageSave);
        imageViewback = findViewById(R.id.imageBack);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initFirebase(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }
}