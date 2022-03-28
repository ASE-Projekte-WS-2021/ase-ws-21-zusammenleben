package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.entities.Flats;
import com.example.myapplication.entities.ShoppingList;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityShoppingList extends AppCompatActivity {

    //array list for data
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> listcosts = new ArrayList<>();
    ListView list_view, list_view_cost;
    ArrayAdapter arrayAdapter, arrayAdapterCosts;
    FirebaseDatabase database;
    DatabaseReference databaseReferenceShop;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReferenceFlat;
    String currentUser;

    ArrayList<ArrayList<String>> flatContents = new ArrayList<>();



    //////////////// marco

    ArrayList<String> IDs = new ArrayList<>();
    ArrayList<String> shoppingItemDescriptions = new ArrayList<>();
    ArrayList<String> shoppingItemCosts = new ArrayList<>();

    ///////////////




    String flatID;
    long shoppinglistCountertwo;

    //int shoppinglistCounter;


    BottomNavigationView bottomNavigationView;

    TextView sumCosts, costCheckout, inputCheckoutName;
    EditText editTextCost, inputItem, costItem, numItem;
    double total = 0.0;
    String result;
    ArrayList<ArrayList<String>> shoppinglistarray = new ArrayList<>();

    int position;
    String day, pos, basketID;
    String[] content;

    CheckedTextView checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist);
        //find view by id
        initFirebase();
        getFlatIDinFirebase();
        initViews();
        initNavigationBar();
        initClickListeners();
        //callOnPayment();
    }

    @Override
    protected void onResume(){
        super.onResume();
        Bundle receivedData = getIntent().getExtras();
        String s = receivedData.getString("VALIDATE");
        unpackArrivedData(s);
        Log.d("ernsthaft?", basketID);
        retrieveShoppingItems();
    }

    private void retrieveShoppingItems(){
        readIDs(new FirebaseFlatCallback() {
            @Override
            public void onCallback(ArrayList<String> arrayList) {
                fillListView();
            }
        });
    }

    private void fillListView(){
        for(int i = 0 ; i < shoppingItemDescriptions.size(); i++){
            String currentDescription = shoppingItemDescriptions.get(i);
            list.add(currentDescription);
            arrayAdapter.notifyDataSetChanged();
        }

        for(int i = 0 ; i < shoppingItemCosts.size(); i++) {
            String currentCost = shoppingItemCosts.get(i);
            listcosts.add(currentCost);
            arrayAdapterCosts.notifyDataSetChanged();
        }

        Log.d("passt das?", list.toString());
        Log.d("passt das?", listcosts.toString());
    }

    private void readIDs(FirebaseFlatCallback firebaseFlatCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                     if(ds.getKey().equals(basketID)){
                         ArrayList<String> myShoppingList = ds.getValue(ShoppingList.class).getData();
                         shoppingItemDescriptions = ds.getValue(ShoppingList.class).getItems();
                         shoppingItemCosts = ds.getValue(ShoppingList.class).getCosts();

                     }
                }
                firebaseFlatCallback.onCallback(IDs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        databaseReferenceShop.addListenerForSingleValueEvent(valueEventListener);
    }

    interface FirebaseFlatCallback{
        void onCallback(ArrayList<String> arrayList);
    }

    private void unpackArrivedData(String str){
        Log.d("validating", str);
        String[] arrivedData = str.split("/");
        basketID = arrivedData[2];
        System.out.println(basketID);
    }

    private void initViews(){
        list_view = findViewById(R.id.list_view);
        list_view_cost = findViewById(R.id.list_view_cost);
        list_view_cost.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list_view_cost.setItemChecked(position, false);
        sumCosts = findViewById(R.id.sumcosts);

        editTextCost = findViewById(R.id.insert_costs);

        arrayAdapter = new ArrayAdapter(ActivityShoppingList.this, android.R.layout.simple_list_item_1, list);
        arrayAdapterCosts = new ArrayAdapter(ActivityShoppingList.this, android.R.layout.simple_list_item_multiple_choice, listcosts);
        list_view.setAdapter(arrayAdapter);
        list_view_cost.setAdapter(arrayAdapterCosts);


        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setSelectedItemId(R.id.shopping);

        inputItem = findViewById(R.id.inputItem);
        costItem = findViewById(R.id.costItem);
        numItem = findViewById(R.id.numItem);

        costCheckout = findViewById(R.id.costCheckout);
        inputCheckoutName = findViewById(R.id.inputNameCheckout);

        checkBox = findViewById(android.R.id.text1);
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

        list_view_cost.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View v, int i, long l) {
                //hier wurde die <Kosten und das Item bereits eingetragen und angezeigt



                list_view_cost.setOnItemClickListener((parent, view, position, id) -> {
                    CheckedTextView checkBox = ((CheckedTextView)view);
                    checkBox.setChecked(!checkBox.isChecked());

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
                return true;
            }
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
            case R.id.checkout_item:
                checkForEmptyList();
                break;
        }
        return true;
    }

    private void checkForEmptyList(){
        if (list.size() != 0 && listcosts.size() != 0){
            ActivityShoppingList.this.checkout();
        }
        else {
            Toast.makeText(ActivityShoppingList.this, "Du hast noch keine Items in deiner Shopping Liste", Toast.LENGTH_SHORT).show();
        }
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
        final EditText numItem = dialogLayout.findViewById(R.id.numItem);

        builder.setPositiveButton("Hinzufügen", (dialog, which) -> {
            if (!inputItem.getText().toString().isEmpty() && !costItem.getText().toString().isEmpty() && !numItem.getText().toString().isEmpty()) {
                if (Integer.parseInt(numItem.getText().toString()) == 1){
                    list.add(inputItem.getText().toString().trim());
                    listcosts.add(costItem.getText().toString().trim());
                    arrayAdapter.notifyDataSetChanged();
                    arrayAdapterCosts.notifyDataSetChanged();
                    addSums();
                }

                else if(Integer.parseInt(numItem.getText().toString()) > 1) {
                    int newCost = Integer.parseInt(costItem.getText().toString());
                    int newNum = Integer.parseInt(numItem.getText().toString());
                    int result = newCost * newNum;

                    String resultStr = Integer.toString(result);
                    costItem.setText(resultStr);

                    list.add(inputItem.getText().toString().trim());
                    listcosts.add(costItem.getText().toString().trim());
                    arrayAdapter.notifyDataSetChanged();
                    arrayAdapterCosts.notifyDataSetChanged();
                    addSums();
                }

            } else {
                inputItem.setError("add item here !");
                costItem.setError("add costs here !");
                numItem.setError("die Anzahl darf nicht null sein");
            }
        });

        Log.d("444 check items", list.toString());

        builder.setNegativeButton("Abbrechen", (dialog, which) -> dialog.dismiss());
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

    /*private void callOnPayment(){
        sumCosts.setOnClickListener(view -> {
            String strSumCosts = sumCosts.getText().toString();
            ArrayList<String> items = new ArrayList<>(list);
            ShoppingList shoppingList = new ShoppingList(items,strSumCosts);
            Intent intent = new Intent(getApplicationContext(), ActivityPaymentOverview.class);
            intent.putExtra("key", strSumCosts);
            startActivity(intent);
            databaseReferenceShop.push().setValue(shoppingList);
        });
    }*/


    private void checkout(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityShoppingList.this);
        builder.setTitle("Checkout");

        View dialogLayoutCheckout = LayoutInflater.from(ActivityShoppingList.this).inflate(R.layout.layout_checkout, null, false);

        builder.setView(dialogLayoutCheckout);

        final TextView inputCheckoutName = dialogLayoutCheckout.findViewById(R.id.inputNameCheckout);
        final TextView costCheckout = dialogLayoutCheckout.findViewById(R.id.costCheckout);
        // ersetzt die Methode callOnPayment
        Intent intentBasket = getIntent();
        String strTextName = "Zweck" + ": ";
        String strTextTitle = intentBasket.getStringExtra("getTitle");
        inputCheckoutName.setText(strTextName + strTextTitle);

        String valueCost = "Gesamtbetrag"+ ": ";
        String valueCostText = sumCosts.getText().toString();
        costCheckout.setText(valueCost + valueCostText);

        builder.setPositiveButton("Abschließen", (dialog, which) -> {
            if (!inputCheckoutName.getText().toString().isEmpty()) {

                ArrayList<String> items = new ArrayList<>(list);
                ArrayList<String> costs = new ArrayList<>(listcosts);
                String flat = flatID;

                Log.d("444 check items again", flatID);

                ShoppingList shoppingList = new ShoppingList(items,costs, valueCostText,flat, strTextTitle);
                readDataFromShoppingList(new FirebaseCallback() {
                    @Override
                    public void onCallback(ArrayList<ArrayList<String>> list) {
                        Log.d("Hi", list.toString());
                        Log.d("Hi", String.valueOf(shoppinglistCountertwo));
                        String shoptitle = basketID;
                        databaseReferenceShop.child(shoptitle).setValue(shoppingList);
                    }
                });

                Intent intent = new Intent(getApplicationContext(), ActivityPaymentOverview.class);
                intent.putExtra("key", valueCostText);
                intent.putExtra("value", strTextTitle);
                startActivity(intent);
                //databaseReferenceShop.push().setValue(shoppingList);



            } else {
                inputCheckoutName.setError("add Name here !");
            }
        });

        builder.setNegativeButton("Abbrechen", (dialog, which) -> dialog.dismiss());
        builder.show();
    }


    private void getFlatIDinFirebase(){
        readData(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<ArrayList<String>> list) {
            }
        });
    }

    private interface FirebaseCallback {
        void onCallback(ArrayList<ArrayList<String>> list);
    }

    private void readDataFromShoppingList(FirebaseCallback firebaseCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    shoppinglistCountertwo = snapshot.getChildrenCount();
                    ArrayList<String> shopping = ds.getValue(ShoppingList.class).getData();
                    if(shopping.contains(flatID)){
                        shoppinglistarray.add(shopping);
                    }
                }

                firebaseCallback.onCallback(shoppinglistarray);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        databaseReferenceShop.addListenerForSingleValueEvent(valueEventListener);
    }

    // Get the data from Firebase Server online
    private void readData(FirebaseCallback firebaseCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    int flatSize = snap.getValue(Flats.class).getFlatSize();
                    ArrayList<String> flatContent = snap.getValue(Flats.class).getData(flatSize);
                    flatContents.add(flatContent);
                }
                // Wait for the server to retrieve the data
                firebaseCallback.onCallback(flatContents);
                getCurrentUserFlat();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        databaseReferenceFlat.addListenerForSingleValueEvent(valueEventListener);
    }

    private void getCurrentUserFlat(){
        for(int i = 0; i < flatContents.size(); i++){
            if(flatContents.get(i).contains(currentUser)) {
                String currentUserFlat = flatContents.get(i).toString();
                content = currentUserFlat.split(",");
            }
        }
        flatID = extractFlatID();
    }

    private String extractFlatID(){
        for(int i = 0 ; i < content.length ; i++){
            String s = content[i];
            s = s.trim();
        }
        return content[0].substring(1);
    }

    private void initFirebase(){
        database = FirebaseDatabase.getInstance("https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReferenceShop = database.getReference("ShoppingList");
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReferenceFlat = database.getReference("Flats");
        currentUser = firebaseAuth.getCurrentUser().getEmail();
    }

}
