package View.After;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import Adapter.ShoppingItemAdapter;
import Entities.Basket;
import Entities.ShoppingItem;
import Presenter.ShoppingList.ShoppingListContract;
import Presenter.ShoppingList.ShoppingListPresenter;
import Utils.RecyclerItemClickListener;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ShoppingItemAdapter mItemsAdapter;

    String transmittedItem, transmittedAmount;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setupUIComponents();
        shoppingListPresenter = new ShoppingListPresenter(this);
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_shoppinglist);
        recyclerView = findViewById(R.id.list_view);
        //listViewNum = findViewById(R.id.list_view_num);
        Log.d("recyclertest", recyclerView.toString());
        toolbar = findViewById(R.id.topAppBar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }


    @Override
    protected void onResume(){
        super.onResume();
        unpackIntentData();
        handleToolBarInteraction();
        Log.d("Rutsche1", basketID);
    }

    private void handleToolBarInteraction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityBasketList.class);
                finish();
                overridePendingTransition(0,0);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                handleAddItemDialog();
                return false;
            }
        });

    }

    //TODO: Dialog auslagern
    private void handleAddItemDialog() {
        /*ShoppingListDialog shoppingListDialog = new ShoppingListDialog();
        shoppingListDialog.show(getSupportFragmentManager(), "dialog");*/
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityShoppingList.this);
        builder.setTitle("Add New Item");

        View dialogLayout = LayoutInflater.from(ActivityShoppingList.this).inflate(R.layout.layout_item_dialog, null, false);

        builder.setView(dialogLayout);
        inputItem = dialogLayout.findViewById(R.id.inputItem);
        numItem = dialogLayout.findViewById(R.id.numItem);

        builder.setPositiveButton("Hinzufügen", (dialog, which) -> {
            if (!inputItem.getText().toString().isEmpty() && !numItem.getText().toString().isEmpty()) {
                transmittedItem = inputItem.getText().toString().trim();
                transmittedAmount = numItem.getText().toString().trim();
                /*Intent intent = new Intent(getApplicationContext(), ActivityShoppingList.class);
                Bundle sendBundle = new Bundle();
                sendBundle.putString("ITEM", transmittedItem);
                sendBundle.putString("AMOUNT", transmittedAmount);
                intent.putExtras(sendBundle);
                startActivity(intent);*/
                addItem();

            } else {
                inputItem.setError("Bitte fügen Sie ein Item hinzu");
                numItem.setError("die Anzahl darf nicht null sein");
            }
        });

        builder.setNegativeButton("Abbrechen", (dialog, which) -> dialog.dismiss());
        builder.show();

        //return builder.create();
    }

    private void addItem(){

        ShoppingItem item = new ShoppingItem(transmittedItem, Integer.parseInt(transmittedAmount), "0");
        shoppingListPresenter.addShoppingItem(basketID, item);
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
    }

    @Override
    public void onShoppingItemAdded(ArrayList<ArrayList<String>> shoppingItems, ArrayList<String> shoppingItemIDs) {
        items = shoppingItems;
        itemIds = shoppingItemIDs;
        Log.d("Recycler", items.toString());

        //NULL Pointer here. Needs to be fixed!

        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mItemsAdapter = new ShoppingItemAdapter(this, items);
        recyclerView.setAdapter(mItemsAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

    }

    @Override
    public void onShoppingItemRetrieved(ArrayList<ShoppingItem> shoppingList) {

    }

    @Override
    public void onShoppingItemAdded(String basketID) {

    }

    @Override
    public void onShoppingListItemAltered(String itemID) {

    }
}
