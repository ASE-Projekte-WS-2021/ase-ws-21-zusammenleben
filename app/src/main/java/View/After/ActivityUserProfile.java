package View.After;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.firebase.database.annotations.Nullable;

import Presenter.UserProfile.UserProfileContract;
import Presenter.UserProfile.UserProfilePresenter;
import View.Before.CreateFlatActivity;
import View.Before.LoginActivity;

public class ActivityUserProfile extends AppCompatActivity implements UserProfileContract.View {

    BottomNavigationView bottomNavigationView;
    MaterialToolbar toolbar;
    Button btnleaving, btninvite;
    ImageView imageView;
    int TAKE_IMAGE_CODE = 10001;

    Bitmap bitmap;

    private UserProfilePresenter mProfilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
        setupNavBar();
        handleTopBar();
        handleClicks();
        mProfilePresenter = new UserProfilePresenter(this);
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
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void handleClicks(){
        btnleaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateFlatActivity.class);
                startActivity(intent);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setpicture();
            }
        });
        btninvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //deepLinks
            }
        });
    }

    private void setpicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(intent, TAKE_IMAGE_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(ActivityUserProfile.this, "Activity not found", Toast.LENGTH_SHORT).show();
        }

    }

    private void picutre(Bitmap bitmap){
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
                    picutre(bitmap);
            }
        }
    }


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

    @Override
    public void onProfileSuccess(String message) {
        Log.d("login", "succesful!");
        picutre(bitmap);
        Toast.makeText(getApplicationContext(), "Profilbild geändert!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProfileFailure(String message) {
        Log.d("login", "succesful!");
        //Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
    }


}
