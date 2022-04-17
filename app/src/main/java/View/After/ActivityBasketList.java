package View.After;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import Utils.BasketDialog;
import Utils.DialogListener;
import Utils.PaymentDialog;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;
import Adapter.BasketAdapter;
import Entities.Basket;
import Entities.Flat;
import Presenter.BasketList.BasketListContract;
import Presenter.BasketList.BasketListPresenter;
import Utils.RecyclerItemClickListener;
import View.Before.LoginActivity;


public class ActivityBasketList extends AppCompatActivity implements BasketListContract.View, BasketListContract.onBasketSuccessListener, BasketAdapter.ItemClickListener, DialogListener {

    //UI components
    BottomNavigationView bottomNavigationView;
    MaterialToolbar toolbar;
    FloatingActionButton basketButton;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    // Architecture components
    BasketListPresenter mBasketListPresenter;
    FirebaseAuth mAuth;
    FirebaseUser user;

    // Util data
    String currentUserEmail;
    Flat currentFlat;
    ArrayList<ArrayList<String>> baskets;
    ArrayList<String> ids;
    BasketAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setupUIComponents();
        mBasketListPresenter = new BasketListPresenter(this);
        setupNavBar();
        handleTopBar();
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_basketlist);
        basketButton = findViewById(R.id.basketActionButton);
        recyclerView = findViewById(R.id.basketRecyclerView);
        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.shopping);
        toolbar = findViewById(R.id.topAppBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    protected void onResume(){
        super.onResume();
        getCurrentUserEmail();
        // start MVP transaction
        mBasketListPresenter.retrieveFlat(currentUserEmail);
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
                        startActivity(new Intent(getApplicationContext(),ActivityUserProfile.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.shopping:
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
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    // Retrieving userEmail right on the screen, simple method
    private void getCurrentUserEmail(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        currentUserEmail = user.getEmail();
    }

    // create the payment and start MVP transaction
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onButtonClicked(){
        basketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baskets.clear();
                mBasketListPresenter.createBasket(currentUserEmail, currentFlat.getId());
                // UI handling
                finish();
                overridePendingTransition(0,0);
                startActivity(getIntent());
                overridePendingTransition(0,0);
            }
        });
    }

    // MVP callback data has arrived, now handle user transaction
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onFlatFound(Flat flat) {
        currentFlat = flat;
        mBasketListPresenter.retrieveBaskets(currentFlat.getId());
        onButtonClicked();
    }


    // populate the recyclerview with the retrieved items
    @Override
    public void onBasketItemFound(ArrayList<ArrayList<String>> basketElements, ArrayList<String> basketIDs) {
        baskets = basketElements;
        ids = basketIDs;
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new BasketAdapter(baskets, this);
        recyclerView.setAdapter(mAdapter);
    }

    // clear the data structure that populates the recyclerview, it is loaded in onResume()
    @Override
    protected void onPause(){
        super.onPause();
        if(baskets != null) {
            baskets.clear();
        }
    }

    @Override
    public void onItemClicked(int position) {
        handleDialog(position);
    }

    // send Data between dialog and activity
    private void handleDialog(int position) {
        baskets.get(position);
        View v = recyclerView.getChildAt(position);
        TextView basketTitle = v.findViewById(R.id.basketTitle);
        String sendData = basketTitle.getText().toString();
        String sendId = ids.get(position);
        BasketDialog basketDialog = new BasketDialog();
        Bundle bundle = new Bundle();
        bundle.putString("BASKETDATA", sendData);
        bundle.putString("BASKETID", sendId);
        basketDialog.setArguments(bundle);
        basketDialog.show(getSupportFragmentManager(), "basketDialog");

    }

    // delete basket, mvp firebase transaction
    @Override
    public void onReturnValue(String id) {
        mBasketListPresenter.deleteBasket(id);
        //UI handling
        finish();
        overridePendingTransition(0,0);
        startActivity(getIntent());
        overridePendingTransition(0,0);
    }


    // interface methods
    @Override
    public void startIntent() {
    }

    @Override
    public void onFlatFoundSuccess(Flat flat) {
    }

    @Override
    public void onBasketAddedSuccess() {
    }

    @Override
    public void onBasketsRetrieved(ArrayList<Basket> baskets) {
    }
}
