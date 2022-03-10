package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityNoteInformations extends AppCompatActivity {

    private TextView ni_title, ni_subtitle, ni_notice;
    ImageView ni_imageViewback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_informations);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ni_title = findViewById(R.id.inputNoteTitle_noteinformation);
        ni_subtitle = findViewById(R.id.noteSubtitle_noteinformation);
        ni_notice = findViewById(R.id.inputNote_noteinformation);

        Intent data= getIntent();


        ni_title.setText(data.getStringExtra("title"));
        ni_subtitle.setText(data.getStringExtra("subtitle"));
        ni_notice.setText(data.getStringExtra("notice"));

        ni_imageViewback = findViewById(R.id.imageBack_noteinformation);
        ni_imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}