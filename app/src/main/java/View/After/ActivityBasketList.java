package View.After;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
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

public class ActivityBasketList extends AppCompatActivity implements BasketListContract.View, BasketListContract.onBasketSuccessListener {

    FloatingActionButton basketButton;
    BasketListPresenter mBasketListPresenter;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String currentUserEmail;
    Flat currentFlat;

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
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_basketlist);
        basketButton = findViewById(R.id.basketActionButton);
        recyclerView = findViewById(R.id.basketRecyclerView);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    protected void onResume(){
        super.onResume();
        getCurrentUserEmail();
        mBasketListPresenter.retrieveFlat(currentUserEmail);

        Log.d("debug1", "View führt Anfrage aus");
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
        Log.d("debug2", "hier ist die flat ID angekommen");
        mBasketListPresenter.retrieveBaskets(currentFlat.getId());
        Log.d("debug3", "jetzt gehts los mit den baskets");
        onButtonClicked();
    }



    /////
    @Override
    public void onBasketItemFound(ArrayList<ArrayList<String>> basketElements, ArrayList<String> basketIDs) {
        baskets = basketElements;
        ids = basketIDs;

        Log.d("ids", ids.toString());


        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new BasketAdapter(this, baskets);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Intent i = new Intent(ActivityBasketList.this, ActivityShoppingList.class);
                Bundle send = new Bundle();
                String sendData = baskets.get(position).toString();
                String sendId = basketIDs.get(position).toString();
                send.putString("BASKETDATA", sendData);
                send.putString("BASKETID", sendId);
                i.putExtras(send);
                startActivity(i);
            }
        }));
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

}

// TODO : Sonntagsliste - Tagesliste?