package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentKt;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.inputmethod.InputMethodManager;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ActivityStartScreen extends AppCompatActivity {

    TextView useremail;
    BottomNavigationView bottomNavigationView;
    FirebaseAuth firebaseAuth;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //try to setup and test my stuff
        setContentView(R.layout.activity_startscreen);

        imageView = findViewById(R.id.imageAddMain);

        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.home);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityStartScreen.this, ActivityNoteSpace.class));
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.payment:
                        startActivity(new Intent(getApplicationContext(),ActivityOverview.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    /*case R.id.add_note:
                        startActivity(new Intent(getApplicationContext(),ActivityNoteSpace.class));
                        overridePendingTransition(0,0);
                        return true;*/
                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(),ActivityUserProfile.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        useremail = findViewById(R.id.show_email);
    }

    private void checkUserStatus (){
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null){
            useremail.setText(user.getEmail());
        } else {
            startActivity(new Intent(ActivityStartScreen.this, ActivityLogin.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }


}