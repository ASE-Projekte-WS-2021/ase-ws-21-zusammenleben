package View.After;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import Adapter.BasketAdapter;
import Adapter.ShoppingItemAdapter;
import Entities.Basket;
import Entities.ShoppingItem;
import Presenter.ShoppingList.ShoppingListContract;
import Presenter.ShoppingList.ShoppingListPresenter;
import Utils.RecyclerItemClickListener;

public class ActivityShoppingList extends AppCompatActivity implements ShoppingListContract.View, ShoppingListContract.onShoppingSuccessListener {

    private ShoppingListPresenter shoppingListPresenter;
    String basketID;
    //die Listen in denen die Items eingefügt werden
    RecyclerView listView;
    //beim checkout wird der name des Baskets angezeigt ohne dass ich ihn verändern kann
    TextView inputCheckoutName;
    //das sind die Inputfelder im dialog builder
    EditText inputItem, numItem;
    MaterialToolbar toolbar;

    ArrayList<ArrayList<String>> items;
    ArrayList<String> itemIds;
    LinearLayoutManager linearLayoutManager;
    ShoppingItemAdapter mItemsAdapter;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setupUIComponents();
        shoppingListPresenter = new ShoppingListPresenter(this);
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_shoppinglist);
        listView = findViewById(R.id.list_view);
        //listViewNum = findViewById(R.id.list_view_num);
        toolbar = findViewById(R.id.topAppBar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }


    @Override
    protected void onResume(){
        super.onResume();
        unpackIntentData();
        handleToolBarInteraction();
    }

    private void handleToolBarInteraction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                addItem();
                return false;
            }
        });

    }

    private void addItem(){
        shoppingListPresenter.addShoppingItem();
    }

    private void checkForEmptyList(){
        // todo
    }

    private void unpackIntentData(){
        Bundle receivedData = getIntent().getExtras();
        basketID = receivedData.getString("BASKETID");
        shoppingListPresenter.retrieveBasketItem(basketID);
    }

    @Override
    public void onBasketItemRetrieved(Basket basket) {

        items = basketElements;
        itemIds = basketIDs;

        //Log.d("ids", ids.toString());


        listView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
        mItemsAdapter = new ShoppingItemAdapter(this, items);
        listView.setAdapter(mItemsAdapter);
        listView.addOnItemTouchListener(new RecyclerItemClickListener(this, listView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        }));

    }

    @Override
    public void onShoppingItemAdded(ShoppingItem item) {

    }

    @Override
    public void onShoppingItemRetrieved(String basketID) {

    }

    @Override
    public void onShoppingItemAdded(String basketID) {

    }

    @Override
    public void onShoppingListItemAltered(String itemID) {

    }
}
