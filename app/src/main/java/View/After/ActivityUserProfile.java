package View.After;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;

import Entities.Flat;
import Presenter.UserProfile.UserProfileContract;
import Presenter.UserProfile.UserProfilePresenter;
import Utils.DialogListener;
import Utils.UserProfileDialog;
import View.Before.CreateFlatActivity;
import View.Before.LoginActivity;

public class ActivityUserProfile extends AppCompatActivity implements UserProfileContract.View, DialogListener {

    // UI components
    BottomNavigationView bottomNavigationView;
    MaterialToolbar toolbar;
    Button btnleaving, btninvite;
    ImageView imageView;
    TextView showMail;
    Bitmap bitmap;
    
    // Architectural
    private UserProfilePresenter mProfilePresenter;

    // Utils
    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    int TAKE_IMAGE_CODE = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        setupNavBar();
        handleTopBar();
        handleClicks();
        retrievePhoto();
        mProfilePresenter = new UserProfilePresenter(this);
    }
    
    // simple lightweight firebase query
    private void retrievePhoto(){
        if (FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null ){
            Glide.with(this)
                    .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                    .into(imageView);
        }
    }
    
    private void setupUIComponents(){
        setContentView(R.layout.activity_userprofile);
        btnleaving = findViewById(R.id.btn_leaving);
        btninvite = findViewById(R.id.btn_invite);
        imageView = findViewById(R.id.headeruser);
        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.wg);
        toolbar = findViewById(R.id.topAppBar);
        showMail = findViewById(R.id.show_email);
        showMail.setText(email);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    // Listen for user interaction
    private void handleClicks(){
        btnleaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProfileDialog userProfileDialog = new UserProfileDialog();
                userProfileDialog.show(getSupportFragmentManager(), "dialog");
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPicture();
            }
        });
        btninvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle send = new Bundle();
                send.putString("EMAIL", email);
                Intent i = new Intent(getApplicationContext(), ActivityPopInvite.class);
                i.putExtras(send);
                startActivity(i);
            }
        });
    }

    // pass image to screen
    private void setPicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(intent, TAKE_IMAGE_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(ActivityUserProfile.this, "Activity not found", Toast.LENGTH_SHORT).show();
        }
    }

    // Start mvp transaction
    private void picture(Bitmap bitmap){
        mProfilePresenter.changePicture(this, bitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_IMAGE_CODE){
            switch (resultCode){
                case RESULT_OK:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bitmap);
                    picture(bitmap);
            }
        }
    }

    // UI method
    private void setupNavBar(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.payment:
                        startActivity(new Intent(getApplicationContext(),ActivityOverview.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.wg:
                        return true;
                    case R.id.shopping:
                        startActivity(new Intent(getApplicationContext(),ActivityBasketList.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }


    // UI method
    private void handleTopBar(){
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.wg_screen:
                        startActivity(new Intent(getApplicationContext(),ActivityUserProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    // Callback completed
    @Override
    public void onProfileSuccess(String message) {
        picture(bitmap);
        Toast.makeText(getApplicationContext(), "Profilbild geändert!", Toast.LENGTH_SHORT).show();
    }


    // interface method
    @Override
    public void onProfileFailure(String message) {
    }

    @Override
    public void onUserDeletedSuccess(String message) {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }

    @Override
    public void onReturnValue(String id) {
        mProfilePresenter.deleteUser(email);
        finish();
        overridePendingTransition(0,0);
        startActivity(getIntent());
        overridePendingTransition(0,0);
    }
}
