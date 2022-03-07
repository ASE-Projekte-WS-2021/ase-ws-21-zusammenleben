package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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


    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notespace);

        setTitle = findViewById(R.id.inputNoteTitle);
        setSubtitle = findViewById(R.id.noteSubtitle);
        setNotice = findViewById(R.id.inputNote);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        dateandtime = findViewById(R.id.textDateTime);

        dateandtime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())
        );

        imageViewsave = findViewById(R.id.imageSave);
        imageViewsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillandsaveNote();
            }
        });

        imageViewback = findViewById(R.id.imageBack);
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

    private void fillandsaveNote(){

        String title = setTitle.getText().toString();
        String subtitle = setSubtitle.getText().toString();
        String notice= setNotice.getText().toString();

        if (title.isEmpty()){
            Toast.makeText(this, "Please enter a note title!", Toast.LENGTH_SHORT).show();
            return;
        }else if (subtitle.isEmpty() && notice.isEmpty()){
            Toast.makeText(this, "Please fill up all informations!", Toast.LENGTH_SHORT).show();
            return;
        }else {
            // TODO Note in eigene Klasse auslagern
            // TODO Connect and prepare object to save in datebase
            // TODO Assign node to flat insteed of current user
            DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("mynotes").document();
            Map<String, Object> note= new HashMap<>();
            note.put("title", title);
            note.put("subtitle", subtitle);
            note.put("notice", notice);

            documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ActivityNoteSpace.this,ActivityStartScreen.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}