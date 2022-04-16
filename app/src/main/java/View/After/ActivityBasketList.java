package View.After;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

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


public class ActivityBasketList extends AppCompatActivity implements BasketListContract.View, BasketListContract.onBasketSuccessListener, BasketAdapter.ItemClickListener {

    FloatingActionButton basketButton;
    BasketListPresenter mBasketListPresenter;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String currentUserEmail;
    Flat currentFlat;
    BottomNavigationView bottomNavigationView;
    MaterialToolbar toolbar;

    ArrayList<ArrayList<String>> baskets;
    ArrayList<String> ids;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
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
        mBasketListPresenter.retrieveFlat(currentUserEmail);

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

    private void getCurrentUserEmail(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        currentUserEmail = user.getEmail();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onButtonClicked(){
        basketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baskets.clear();
                mBasketListPresenter.createBasket(currentUserEmail, currentFlat.getId());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onFlatFound(Flat flat) {
        currentFlat = flat;
        mBasketListPresenter.retrieveBaskets(currentFlat.getId());
        onButtonClicked();
    }

    /////
    @Override
    public void onBasketItemFound(ArrayList<ArrayList<String>> basketElements, ArrayList<String> basketIDs) {
        baskets = basketElements;
        ids = basketIDs;
        Log.d("RecyclerTest", basketElements.toString());

        Log.d("RecyclerTest", recyclerView.toString());

        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new BasketAdapter(baskets, this);
        recyclerView.setAdapter(mAdapter);
    }

    ////

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

    @Override
    protected void onPause(){
        super.onPause();
        if(baskets != null) {
            baskets.clear();
        }

    }

    @Override
    public void onItemClicked(int position) {
        baskets.get(position);
        Intent i = new Intent(ActivityBasketList.this, ActivityShoppingList.class);
        Bundle send = new Bundle();
        String sendData = baskets.get(position).toString();
        String sendId = ids.get(position).toString();
        send.putString("BASKETDATA", sendData);
        send.putString("BASKETID", sendId);
        i.putExtras(send);
        startActivity(i);
    }
}

// TODO : Sonntagsliste - Tagesliste?