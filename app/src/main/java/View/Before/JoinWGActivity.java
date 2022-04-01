package View.Before;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wgfinance.R;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class JoinWGActivity extends AppCompatActivity {

    Button joinWG, findWG;
    EditText flatName;
    TextView foundFlatAddress, foundFlatOwner, foundFlatPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIComponents();
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_join_flat);
        joinWG = findViewById(R.id.btn_joinwg);
        flatName = findViewById(R.id.flat_name);
        findWG = findViewById(R.id.btn_findwg);
        foundFlatAddress = findViewById(R.id.found_flat_address);
        foundFlatOwner = findViewById(R.id.found_flat_owner);
        foundFlatPeople = findViewById(R.id.found_flat_people);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}