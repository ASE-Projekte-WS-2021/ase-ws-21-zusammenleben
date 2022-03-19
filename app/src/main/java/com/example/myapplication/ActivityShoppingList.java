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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist);
        //find view by id
        list_view = findViewById(R.id.list_view);
        list_view_cost = findViewById(R.id.list_view_cost);
        sumCosts = findViewById(R.id.sumcosts);

        arrayAdapter = new ArrayAdapter(ActivityShoppingList.this, android.R.layout.simple_list_item_1, list);
        arrayAdapterCosts = new ArrayAdapter(ActivityShoppingList.this, android.R.layout.simple_list_item_1, listcosts);
        list_view.setAdapter(arrayAdapter);
        list_view_cost.setAdapter(arrayAdapterCosts);


        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.shopping);

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
        initClickListeners();
    }

    private void initClickListeners(){
        list_view.setOnItemClickListener((parent, view, position, id) -> {

            PopupMenu popupMenu = new PopupMenu(ActivityShoppingList.this, view);
            popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {

                switch (item.getItemId()) {

                    case R.id.item_update:
                        //function for update
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityShoppingList.this);
                        View view1 = LayoutInflater.from(ActivityShoppingList.this).inflate(R.layout.item_dialog, null, false);
                        builder.setTitle("Update Item");
                        final EditText editText = view1.findViewById(R.id.etItem);
                        editText.setText(list.get(position));


                        //set custome view to dialog
                        builder.setView(view1);

                        builder.setPositiveButton("Update", (dialog, which) -> {
                            if (!editText.getText().toString().isEmpty()) {
                                list.set(position, editText.getText().toString().trim());
                                arrayAdapter.notifyDataSetChanged();
                                Toast.makeText(ActivityShoppingList.this, "Item Updated!", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                editText.setError("Füge hier das Element ein!");
                            }
                        });

                        builder.setNegativeButton("Abbrechen", (dialog, which) -> dialog.dismiss());

                        builder.show();

                        break;

                    case R.id.item_del:
                        //function for delete
                        Toast.makeText(ActivityShoppingList.this, "Das Element wurde entfernt", Toast.LENGTH_SHORT).show();
                        list.remove(position);
                        listcosts.remove(position);
                        arrayAdapter.notifyDataSetChanged();

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
                        View view1 = LayoutInflater.from(ActivityShoppingList.this).inflate(R.layout.item_dialog, null, false);
                        builder.setTitle("Update Cost");
                        final EditText costItem= view1.findViewById(R.id.costItem);
                        costItem.setText(listcosts.get(position));

                        //set custome view to dialog
                        builder.setView(view1);

                        builder.setPositiveButton("Update", (dialog, which) -> {
                            if ( !costItem.getText().toString().isEmpty()) {
                                listcosts.set(position, costItem.getText().toString().trim());
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

                    case R.id.item_del:
                        //function for delete
                        Toast.makeText(ActivityShoppingList.this, "Das Element wurde entfernt", Toast.LENGTH_SHORT).show();
                        listcosts.remove(position);
                        list.remove(position);
                        arrayAdapterCosts.notifyDataSetChanged();

                        break;

                }

                return true;
            });
            popupMenu.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
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

        View v = LayoutInflater.from(ActivityShoppingList.this).inflate(R.layout.item_dialog, null, false);

        builder.setView(v);
        final EditText etItem = v.findViewById(R.id.etItem);
        final EditText costItem = v.findViewById(R.id.costItem);
        builder.setPositiveButton("Add", (dialog, which) -> {
            if (!etItem.getText().toString().isEmpty() && !costItem.getText().toString().isEmpty()) {
                list.add(etItem.getText().toString().trim());
                listcosts.add(costItem.getText().toString().trim());
                arrayAdapter.notifyDataSetChanged();
                arrayAdapterCosts.notifyDataSetChanged();
                addSums();

            } else {
                etItem.setError("add item here !");
                costItem.setError("add item here !");
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();

    }

    private void addSums(){
        /*double total = 0.0;
        for (int i =0; i < listcosts.size(); i++){
            total = Double.parseDouble(total + listcosts.get(i));
        }*/
        sumCosts.setText("HI");
    }
}
