package com.example.myapplication;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
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
    ImageView imageNote;
    ImageView imageAddImage;

    private String selectedImagePath;


    private static final int REQUEST_CODE_STORAGE_PERMISSION =1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    int TAKE_IMAGE_CODE = 10001;


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

        imageNote= findViewById(R.id.imageNote);
        imageAddImage = findViewById(R.id.imageAddImage);

        selectedImagePath = "";

        imageAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImageClicked();
            }
        });

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
            note.put("imagePath", selectedImagePath);

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

    private void onImageClicked() {
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(
                            ActivityNoteSpace.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION
                    );
                } else {
                    selectImage();
                }
        Toast.makeText(this, "KClicked on Image", Toast.LENGTH_LONG).show();
    }

    private void selectImage(){
        //Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //if(intent.resolveActivity(getPackageManager()) != null){
            //startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        //}
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(intent, TAKE_IMAGE_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(ActivityNoteSpace.this, "Activity not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectImage();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && requestCode == RESULT_OK) {
            if (data != null) {
                Uri selectImageUri = data.getData();
                if (selectImageUri != null) {
                    try {

                        InputStream inputStream = getContentResolver().openInputStream(selectImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageNote.setImageBitmap(bitmap);
                        imageNote.setVisibility(View.VISIBLE);

                        selectedImagePath = getPathFromURI(selectImageUri);

                    } catch (Exception exception) {
                        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
    private String getPathFromURI (Uri contentUri){
        String filePath;
        Cursor cursor = getContentResolver()
                .query(contentUri, null, null, null, null);
        if (cursor == null) {
            filePath = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }
}