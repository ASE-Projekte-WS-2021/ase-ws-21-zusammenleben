package View.After;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import Adapter.ShoppingItemAdapter;
import Entities.Basket;
import Entities.ShoppingItem;
import Presenter.ShoppingList.ShoppingListContract;
import Presenter.ShoppingList.ShoppingListPresenter;
import Utils.DialogListener;
import Utils.RecyclerItemClickListener;
import Utils.ShoppingItemDialog;

public class ActivityShoppingList extends AppCompatActivity implements ShoppingListContract.View, ShoppingListContract.onShoppingSuccessListener, DialogListener {

    // UI components
    EditText inputItem, numItem;
    MaterialToolbar toolbar;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ShoppingItemAdapter mItemsAdapter;
    BottomNavigationView bottomNavigationView;

    // Architectural
    private ShoppingListPresenter shoppingListPresenter;

    // Util data
    String basketID, transmittedItem, transmittedAmount;
    ArrayList<ArrayList<String>> items;
    ArrayList<String> itemIds;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setupUIComponents();
        shoppingListPresenter = new ShoppingListPresenter(this);
    }

    private void setupUIComponents(){
        setContentView(R.layout.activity_shoppinglist);
        recyclerView = findViewById(R.id.list_view);
        toolbar = findViewById(R.id.topAppBar);
        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.shopping);
        setupNavBar();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }


    @Override
    protected void onResume(){
        super.onResume();
        unpackIntentData();
        handleToolBarInteraction();
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

                switch (item.getItemId()) {

                    case R.id.add_item:
                        handleAddItemDialog();
                        break;
                    case R.id.checkout_item:
                        //checkForEmptyList();
                        break;
                }
                return true;
            }
        });

    }

    // handle the dialog
    private void handleAddItemDialog() {
        // initialization
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityShoppingList.this, R.style.AlertDialogStyle);
        builder.setTitle("Neues Item hinzufügen");
        View dialogLayout = LayoutInflater.from(ActivityShoppingList.this).inflate(R.layout.layout_item_dialog, null, false);
        builder.setView(dialogLayout);
        inputItem = dialogLayout.findViewById(R.id.inputItem);
        numItem = dialogLayout.findViewById(R.id.numItem);

        // interaction
        builder.setPositiveButton("Hinzufügen", (dialog, which) -> {
            if (!inputItem.getText().toString().isEmpty() && !numItem.getText().toString().isEmpty()) {
                transmittedItem = inputItem.getText().toString().trim();
                transmittedAmount = numItem.getText().toString().trim();
                addItem();

            } else {
                inputItem.setError("Bitte füge ein Item hinzu");
                numItem.setError("die Anzahl darf nicht null sein");
            }
        });
        builder.setNegativeButton("Abbrechen", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    // Start mvp transaction
    private void addItem(){
        ShoppingItem item = new ShoppingItem(transmittedItem, Integer.parseInt(transmittedAmount), "0");
        shoppingListPresenter.addShoppingItem(basketID, item);
        // UI handling
        finish();
        overridePendingTransition(0,0);
        startActivity(getIntent());
        overridePendingTransition(0,0);
    }

    // handle Intent data, start mvp transaction
    private void unpackIntentData(){
        Bundle receivedData = getIntent().getExtras();
        basketID = receivedData.getString("BASKETID");
        shoppingListPresenter.retrieveBasketItem(basketID);
    }

    // Callback data arrived back in Activity
    @Override
    public void onShoppingItemAdded(ArrayList<ArrayList<String>> shoppingItems, ArrayList<String> shoppingItemIDs) {
        items = shoppingItems;
        itemIds = shoppingItemIDs;
        // populate the recyclerview with data from callback
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mItemsAdapter = new ShoppingItemAdapter(this, items);
        recyclerView.setAdapter(mItemsAdapter);

        // handle which item has been clicked on
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onLongItemClick(View view, int position) {
                String sendId = itemIds.get(position);
                handleItemDialog(sendId);
            }
        }));
    }

    // attach data
    private void handleItemDialog(String itemId){
        ShoppingItemDialog shoppingItemDialog = new ShoppingItemDialog();
        Bundle bundle = new Bundle();
        bundle.putString("ITEMID", itemId);
        shoppingItemDialog.setArguments(bundle);
        shoppingItemDialog.show(getSupportFragmentManager(), "itemDialog");
    }

    // mvp transaction
    @Override
    public void onReturnValue(String itemId) {
        shoppingListPresenter.deleteShoppingListItem(itemId, basketID);
        // UI handling
        finish();
        overridePendingTransition(0,0);
        startActivity(getIntent());
        overridePendingTransition(0,0);
    }

    // interface methods
    @Override
    public void onShoppingItemRetrieved(ArrayList<ShoppingItem> shoppingList) {}

    @Override
    public void onShoppingItemAdded(String basketID) {}

    @Override
    public void onShoppingListItemAltered(String itemID) {}

    @Override
    public void onShoppingItemDeleted(ShoppingItem item) {}

    @Override
    public void onBasketItemRetrieved(Basket basket) {}
}
