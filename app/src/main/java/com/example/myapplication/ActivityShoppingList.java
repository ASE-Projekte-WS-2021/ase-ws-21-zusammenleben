package com.example.myapplication;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ActivityShoppingList extends AppCompatActivity {

    //array list for data
    ArrayList<String> list = new ArrayList<>();
    ListView list_view;
    ArrayAdapter arrayAdapter;

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist);
        //find view by id
        list_view = findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter(ActivityShoppingList.this, android.R.layout.simple_list_item_1, list);
        list_view.setAdapter(arrayAdapter);

        //
        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.shopping);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
            }
        });

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                PopupMenu popupMenu = new PopupMenu(ActivityShoppingList.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.item_update:
                                //function for update
                                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityShoppingList.this);
                                View v = LayoutInflater.from(ActivityShoppingList.this).inflate(R.layout.item_dialog, null, false);
                                builder.setTitle("Update Item");
                                final EditText editText = v.findViewById(R.id.etItem);
                                editText.setText(list.get(position));

                                //set custome view to dialog
                                builder.setView(v);

                                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (!editText.getText().toString().isEmpty()) {
                                            list.set(position, editText.getText().toString().trim());
                                            arrayAdapter.notifyDataSetChanged();
                                            Toast.makeText(ActivityShoppingList.this, "Item Updated!", Toast.LENGTH_SHORT).show();

                                        } else {
                                            editText.setError("add item here !");
                                        }
                                    }
                                });

                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                builder.show();

                                break;

                            case R.id.item_del:
                                //fucntion for del
                                Toast.makeText(ActivityShoppingList.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                                list.remove(position);
                                arrayAdapter.notifyDataSetChanged();

                                break;

                        }

                        return true;
                    }
                });

                //don't forgot this
                popupMenu.show();

            }
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
                _addItem();
                break;

        }

        return true;
    }

    /*
     * method for adding item
     * */
    private void _addItem() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityShoppingList.this);
        builder.setTitle("Add New Item");

        View v = LayoutInflater.from(ActivityShoppingList.this).inflate(R.layout.item_dialog, null, false);

        builder.setView(v);
        final EditText etItem = v.findViewById(R.id.etItem);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!etItem.getText().toString().isEmpty()) {
                    list.add(etItem.getText().toString().trim());
                    arrayAdapter.notifyDataSetChanged();

                } else {
                    etItem.setError("add item here !");
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();

    }
}
