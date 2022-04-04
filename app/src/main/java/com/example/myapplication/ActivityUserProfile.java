package com.example.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.entities.Pop;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ActivityUserProfile extends AppCompatActivity {

    Button button_signout;
    Button button_leaving;
    Button btn_inviteFriends;
    BottomNavigationView bottomNavigationView;
    FirebaseAuth firebaseAuth;
    TextView useremail;
    int TAKE_IMAGE_CODE = 10001;
    //private CircleImageView circleImageView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.ButtonColor));
        }

        firebaseAuth = FirebaseAuth.getInstance();
        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.user);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.getPhotoUrl() != null ){
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .into(imageView);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setpicture();
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.payment:
                        startActivity(new Intent(getApplicationContext(), ActivityOverview.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), ActivityStartScreen.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.shopping:
                        startActivity(new Intent(getApplicationContext(),ActivityBasketList.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.user:
                        return true;
                }
                return false;
            }
        });

        /*button_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                checkUserStatus();

            }
        })*/

        button_leaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityCreateWG.class);
                startActivity(intent);
            }
        });

    }

    private void setupUIComponents() {
        setContentView(R.layout.activity_userprofile);
        //button_signout = (Button) findViewById(R.id.btn_logout);
        button_leaving = (Button) findViewById(R.id.btn_leaving);
        useremail = findViewById(R.id.show_email);
        //circleImageView =findViewById(R.id.user_picture);
        imageView = findViewById(R.id.headeruser);
        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.user);
        btn_inviteFriends = findViewById(R.id.btn_invite);
    }

    private void checkUserStatus (){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            useremail.setText(user.getEmail());
        } else {
            startActivity(new Intent(ActivityUserProfile.this, ActivityLogin.class));
            finish();
        }
    }

    private void setpicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(intent, TAKE_IMAGE_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(ActivityUserProfile.this, "Activity not found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_IMAGE_CODE){

            switch (resultCode){
                case RESULT_OK:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bitmap);
                    uploadimage(bitmap);
            }
        }
    }

    private void uploadimage(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,byteArrayOutputStream);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("images")
                .child(uid + ".jpeg");
        reference.putBytes(byteArrayOutputStream.toByteArray())

                //final ProgressDialog pd = new ProgressDialog(this);
                //pd.setTitle("Upload");
                //pd.show();
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //pd.dismiss();
                        getDownloadUrl(reference);
                        Snackbar.make(findViewById(android.R.id.content), "Picture successfully changed", Snackbar.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void getDownloadUrl(StorageReference reference){
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //Log.d(TAG, "onSuccess:" + uri);
                        Toast.makeText(ActivityUserProfile.this, "Succesfully", Toast.LENGTH_SHORT).show();
                        setUserProfileUrl(uri);
                    }
                });

    }

    private void setUserProfileUrl(Uri uri){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ActivityUserProfile.this, "Succesfully", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ActivityUserProfile.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }

    ///Marco
    @Override
    protected void onResume() {
        super.onResume();
        btn_inviteFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityUserProfile.this, Pop.class));
            }
        });
    }
    ///
}