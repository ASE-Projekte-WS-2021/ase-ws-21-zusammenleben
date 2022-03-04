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

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;


public class ActivityNoteSpace extends AppCompatActivity{

        private EditText setTitle, setSubtitle, setText;
        private TextView dateandtime;


        int noteId;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_notespace);

            setTitle = findViewById(R.id.inputNoteTitle);
            setSubtitle = findViewById(R.id.noteSubtitle);
            setText = findViewById(R.id.inputNote);
            dateandtime = findViewById(R.id.textDateTime);

            dateandtime.setText(
                    new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                    .format(new Date())
            );

            ImageView imageViewback = findViewById(R.id.imageBack);
            imageViewback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            /*

            TextView noteText = findViewById(R.id.noteText);

            // Fetch data that is passed from MainActivity
            Intent intent = getIntent();

            // Accessing the data using key and value
            noteId = intent.getIntExtra("noteId", -1);
            if (noteId != -1) {
                noteText.setText(ActivityStartScreen.notes.get(noteId));
            } else {

                MainActivity.notes.add("");
                noteId = MainActivity.notes.size() - 1;
                MainActivity.arrayAdapter.notifyDataSetChanged();

            }

            noteText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // add your code here
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    MainActivity.notes.set(noteId, String.valueOf(charSequence));
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    // Creating Object of SharedPreferences to store data in the phone
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                    HashSet<String> set = new HashSet(MainActivity.notes);
                    sharedPreferences.edit().putStringSet("notes", set).apply();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    // add your code here
                }
            });*/
        }

}
