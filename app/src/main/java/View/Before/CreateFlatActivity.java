package View.Before;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class CreateFlatActivity extends AppCompatActivity{

    Button btnCreateNewFlat, btnJoinFlat;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setupUIComponents();
        handleClicks();
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_createflat);
        btnCreateNewFlat = (Button) findViewById(R.id.btn_createNewGroup);
        btnJoinFlat = (Button) findViewById(R.id.btn_inviteYourFriends);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void handleClicks(){
        btnCreateNewFlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("clicked", "create");
                Intent create = new Intent(CreateFlatActivity.this, AddFlatActivity.class);
                startActivity(create);
            }
        });

        btnJoinFlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("clicked", "join");
                Intent join = new Intent(CreateFlatActivity.this, JoinFlatActivity.class);
                startActivity(join);
            }
        });
    }
}
