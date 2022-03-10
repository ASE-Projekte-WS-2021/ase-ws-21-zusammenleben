package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ActivityEditNote extends AppCompatActivity {

    Intent data;
    EditText edit_title,edit_subtitle, edit_note;
    ImageView imagesave;
    ImageView imageback;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnote);
        edit_title = findViewById(R.id.Title_EditNote);
        edit_subtitle = findViewById(R.id.Subtitle_EditNote);
        edit_note = findViewById(R.id.Note_EditNote);
        imagesave = findViewById(R.id.imageSave_EditNote);
        imageback = findViewById(R.id.imageBack_EditNote);
        data=getIntent();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imagesave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String change_title = edit_title.getText().toString();
                String change_subtitle = edit_subtitle.getText().toString();
                String change_note = edit_note.getText().toString();

                if (change_title.isEmpty()||change_subtitle.isEmpty()||change_note.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter all required informations", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("mynotes").document(data.getStringExtra("noteID"));
                    Map<String, Object> note = new HashMap<>();
                    note.put("title", change_title);
                    note.put("subtitle", change_subtitle);
                    note.put("notice", change_note);
                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Succesfully updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ActivityEditNote.this,ActivityStartScreen.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        String note_title = data.getStringExtra("title");
        String note_subtitle = data.getStringExtra("subtitle");
        String note_notice = data.getStringExtra("notice");
        edit_title.setText(note_title);
        edit_subtitle.setText(note_subtitle);
        edit_note.setText(note_notice);
    }
}