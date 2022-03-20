package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
    private LinearLayout imageNote;
    private ImageView imageInput;
    private ImageView imageAddImage;
    private ImageView imageAddUrl;
    private TextView textWebUrl;
    private LinearLayout layoutWebUrl;

    public String selectedImagePath;

    private ImageView addLocation;

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    int TAKE_IMAGE_CODE = 10001;

    private AlertDialog dialogAddUrl;


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
        imageInput = findViewById(R.id.imageNoteInput);
        imageAddImage = findViewById(R.id.imageAddImage);


        textWebUrl = findViewById(R.id.textWebURL);
        layoutWebUrl = findViewById(R.id.layoutWebUrl);
        imageAddUrl = findViewById(R.id.imageAddWebLink);

        addLocation = findViewById(R.id.imageAddLocation);

        selectedImagePath = "";

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

        initMenu();
    }

    private void initMenu(){
        imageAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImageClicked();
                System.out.println("image was clicked");
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Permission was questioned");
                    ActivityCompat.requestPermissions(
                            ActivityNoteSpace.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION
                    );
                } else {
                    System.out.println("Permission was not questioned");
                    selectImage();
                }
            }
         });

        imageAddUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddURLDialog();
            }
        });
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityNoteSpace.this, ActivityShowLocation.class);
                startActivity(intent);
            }
        });
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
        Toast.makeText(this, "Clicked on Image", Toast.LENGTH_LONG).show();
    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }
        /*Intent intents = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(intents, TAKE_IMAGE_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(ActivityNoteSpace.this, "Activity not found", Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        System.out.println("onRequestPermissionResult was called");
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectImage();
        }
        else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("onActivityResult was called");
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    try {
                        System.out.println("bitmapping was called");

                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageInput.setImageBitmap(bitmap);
                        imageNote.setVisibility(View.VISIBLE);

                        selectedImagePath = getPathFromURI(selectedImageUri);

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

    private void fillandsaveNote(){

        String title = setTitle.getText().toString().trim();
        String subtitle = setSubtitle.getText().toString().trim();
        String notice= setNotice.getText().toString().trim();
        String url = textWebUrl.getText().toString().trim();
        String pathToImage = selectedImagePath;

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
            note.put("imagePath", pathToImage);
            note.put("url", url);

            if(layoutWebUrl.getVisibility() == View.VISIBLE){
                note.put("url", url);
                System.out.println("weburl is visible");
            }

            documentReference.set(note).addOnSuccessListener(unused -> {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
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
}