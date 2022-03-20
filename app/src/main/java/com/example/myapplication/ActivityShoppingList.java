package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ActivityShoppingList extends AppCompatActivity {

    //array list for data
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> listcosts = new ArrayList<>();
    ListView list_view, list_view_cost;
    ArrayAdapter arrayAdapter, arrayAdapterCosts;

    BottomNavigationView bottomNavigationView;

    TextView sumCosts;
    EditText editTextCost, inputItem, costItem, numItem;
    double total;
    String result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist);
        //find view by id
        initViews();
        initNavigationBar();
        initClickListeners();
        callOnPayment();
    }

    private void initViews(){
        list_view = findViewById(R.id.list_view);
        list_view_cost = findViewById(R.id.list_view_cost);
        sumCosts = findViewById(R.id.sumcosts);

        editTextCost = findViewById(R.id.insert_costs);

        arrayAdapter = new ArrayAdapter(ActivityShoppingList.this, android.R.layout.simple_list_item_1, list);
        arrayAdapterCosts = new ArrayAdapter(ActivityShoppingList.this, android.R.layout.simple_list_item_1, listcosts);
        list_view.setAdapter(arrayAdapter);
        list_view_cost.setAdapter(arrayAdapterCosts);

        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.shopping);

        inputItem = findViewById(R.id.inputItem);
        costItem = findViewById(R.id.costItem);
        numItem = findViewById(R.id.numItem);
    }

    private void initNavigationBar(){
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.payment:
                    startActivity(new Intent(getApplicationContext(),ActivityOverview.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(),ActivityStartScreen.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.shopping:
                    return true;
                case R.id.user:
                    startActivity(new Intent(getApplicationContext(),ActivityUserProfile.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });
    }

    private void initClickListeners(){
        list_view.setOnItemClickListener((parent, view, position, id) -> {

            PopupMenu popupMenu = new PopupMenu(ActivityShoppingList.this, view);
            popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {

                switch (item.getItemId()) {

                    case R.id.item_update:
                        //function for the update on the input. Solution via AlertDialog.
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityShoppingList.this);
                        View dialogOnUpdate = LayoutInflater.from(ActivityShoppingList.this).inflate(R.layout.layout_item_dialog, null, false);
                        builder.setTitle("Update Item");
                        final EditText inputItem = dialogOnUpdate.findViewById(R.id.inputItem);
                        //noch für kosten einsetzen!!
                        inputItem.setText(list.get(position));
                        final EditText costItem= dialogOnUpdate.findViewById(R.id.costItem);
                        costItem.setText(listcosts.get(position));

                        builder.setView(dialogOnUpdate);

                        builder.setPositiveButton("Update", (dialog, which) -> {
                            if (!inputItem.getText().toString().isEmpty() && !costItem.getText().toString().isEmpty()) {
                                list.set(position, inputItem.getText().toString().trim());
                                listcosts.set(position, costItem.getText().toString().trim());
                                arrayAdapter.notifyDataSetChanged();
                                arrayAdapterCosts.notifyDataSetChanged();
                                Toast.makeText(ActivityShoppingList.this, "Item Updated!", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                inputItem.setError("Füge hier das Element ein!");
                            }
                        });

                        builder.setNegativeButton("Abbrechen", (dialog, which) -> dialog.dismiss());

                        builder.show();

                        break;

                    case R.id.item_delete:
                        //function for delete
                        Toast.makeText(ActivityShoppingList.this, "Das Element wurde entfernt", Toast.LENGTH_SHORT).show();
                        list.remove(position);
                        listcosts.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                        arrayAdapterCosts.notifyDataSetChanged();

                        break;

                }

                return true;
            });
            popupMenu.show();
        });

        list_view_cost.setOnItemClickListener((parent, view, position, id) -> {

            PopupMenu popupMenu = new PopupMenu(ActivityShoppingList.this, view);
            popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {

                switch (item.getItemId()) {

                    case R.id.item_update:
                        //function for update
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityShoppingList.this);
                        View layoutDialogUpdate = LayoutInflater.from(ActivityShoppingList.this).inflate(R.layout.layout_item_dialog, null, false);
                        builder.setTitle("Update Cost");
                        final EditText costItem= layoutDialogUpdate.findViewById(R.id.costItem);
                        costItem.setText(listcosts.get(position));
                        final EditText editText = layoutDialogUpdate.findViewById(R.id.inputItem);
                        editText.setText(list.get(position));

                        //set custom view to dialog
                        builder.setView(layoutDialogUpdate);

                        builder.setPositiveButton("Update", (dialog, which) -> {
                            if ( !costItem.getText().toString().isEmpty() && !!inputItem.getText().toString().isEmpty()) {
                                list.set(position, inputItem.getText().toString().trim());
                                listcosts.set(position, costItem.getText().toString().trim());
                                arrayAdapter.notifyDataSetChanged();
                                arrayAdapterCosts.notifyDataSetChanged();
                                Toast.makeText(ActivityShoppingList.this, "Item Updated!", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                costItem.setError("Füge hier den Preis ein!");
                            }
                        });

                        builder.setNegativeButton("Abbrechen", (dialog, which) -> dialog.dismiss());

                        builder.show();

                        break;

                    case R.id.item_delete:
                        //function for delete
                        Toast.makeText(ActivityShoppingList.this, "Das Element wurde entfernt", Toast.LENGTH_SHORT).show();
                        listcosts.remove(position);
                        list.remove(position);
                        arrayAdapterCosts.notifyDataSetChanged();
                        arrayAdapter.notifyDataSetChanged();

                        break;

                }

                return true;
            });
            popupMenu.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_add_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.add_item:
                //function to add
                addItem();
                break;
        }
        return true;
    }

    /*
     * method for adding item
     * */
    private void addItem() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityShoppingList.this);
        builder.setTitle("Add New Item");

        View dialogLayout = LayoutInflater.from(ActivityShoppingList.this).inflate(R.layout.layout_item_dialog, null, false);

        builder.setView(dialogLayout);
        final EditText inputItem = dialogLayout.findViewById(R.id.inputItem);
        final EditText costItem = dialogLayout.findViewById(R.id.costItem);
        String item = costItem.getText().toString().trim();
        int cost = calculateItemCosts() *  Integer.getInteger(item);
        System.out.println(cost);


        builder.setPositiveButton("Add", (dialog, which) -> {
            if (!inputItem.getText().toString().isEmpty() && !costItem.getText().toString().isEmpty()) {
                list.add(inputItem.getText().toString().trim());
                //listcosts.add(item);
                listcosts.add(costItem.getText().toString().trim());
                arrayAdapter.notifyDataSetChanged();
                arrayAdapterCosts.notifyDataSetChanged();
                //calculateItemCosts();
                addSums();

            } else {
                inputItem.setError("add item here !");
                costItem.setError("add costs here !");
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();

    }

    private void addSums(){

        total =  0.0;
        for (int i =0; i < listcosts.size(); i++){
            total = total + Double.parseDouble( listcosts.get(i));
        }
        result = String.valueOf(total);
        sumCosts.setText(result);
    }

    private void callOnPayment(){
        sumCosts.setOnClickListener(view -> {
            String strSumCosts = sumCosts.getText().toString();
            Intent intent = new Intent(getApplicationContext(), ActivityPaymentOverview.class);
            intent.putExtra("key", strSumCosts);
            startActivity(intent);
        });
    }

    private int calculateItemCosts() {
        String item  = numItem.getText().toString().trim();
        int multiplikator = Integer.parseInt(item);
        if (!item.isEmpty()) {
            System.out.print(multiplikator);
            return multiplikator;
        }
        System.out.print(multiplikator-10);
        return 1;
    }
}
