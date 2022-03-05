package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;


public class ActivityNoteSpace extends AppCompatActivity{

        private EditText inputNoteTitle, inputNoteSubtitle, inputNoteText;
        private TextView textDateTime;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_notespace);

            ImageView imageBack= findViewById(R.id.imageBack);
            imageBack.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick (View view){
                    onBackPressed();
                }
                });
            inputNoteTitle = findViewById(R.id.inputNoteTitle);
            inputNoteSubtitle = findViewById(R.id.noteSubtitle);
            inputNoteText = findViewById(R.id.inputNote);

            textDateTime = findViewById(R.id.textDateTime);

            textDateTime.setText(
                    new SimpleDateFormat("EEEE, dd MMMM, yyyy HH:mm a", Locale.getDefault()).format(new Date())
            );
            }

            private void saveNote (){
            if (inputNoteTitle.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Notetitle cant be empty", Toast.LENGTH_LONG).show();
            return;
                } else if (inputNoteSubtitle.getText().toString().trim().isEmpty() && inputNoteText.getText().toString().trim().isEmpty()){
                Toast.makeText(this, "Note cant be empty", Toast.LENGTH_LONG).show();
                return;
            }

            final Note note = new Note;
            note.setTitle
            }
}
