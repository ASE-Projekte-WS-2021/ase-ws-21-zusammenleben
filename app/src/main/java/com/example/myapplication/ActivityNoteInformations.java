package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityNoteInformations extends AppCompatActivity {

    private TextView ni_title, ni_subtitle, ni_notice, ni_url;
    ImageView ni_imageViewback;
    Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        setimageViewback();
        shownotes();
    }

    public void shownotes(){
        ni_title.setText(data.getStringExtra("title"));
        ni_subtitle.setText(data.getStringExtra("subtitle"));
        ni_notice.setText(data.getStringExtra("notice"));
        ni_url.setText(data.getStringExtra("url"));
    }

    public void setimageViewback(){
        ni_imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_note_informations);
        ni_title = findViewById(R.id.inputNoteTitle_noteinformation);
        ni_subtitle = findViewById(R.id.noteSubtitle_noteinformation);
        ni_notice = findViewById(R.id.inputNote_noteinformation);
        ni_url = findViewById(R.id.noteurl_information);
        ni_imageViewback = findViewById(R.id.imageBack_noteinformation);
        data=getIntent();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}