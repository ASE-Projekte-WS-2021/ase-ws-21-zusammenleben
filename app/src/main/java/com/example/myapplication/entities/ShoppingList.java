package com.example.myapplication.entities;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ShoppingList {

    private ArrayList<String> items;
    ArrayAdapter arrayAdapter;
    private String strSumCosts;
    private String flat;

    public ShoppingList () {
    }

    public ShoppingList (ArrayList<String> items, String strSumCosts, String flat) {
        this.items = items;
        this.strSumCosts = strSumCosts;
        this.flat = flat;

    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public ArrayAdapter getArrayAdapter() {
        return arrayAdapter;
    }

    public void setStrSumCosts(String strSumCosts) {
        this.strSumCosts = strSumCosts;
    }

    public void setArrayAdapter(ArrayAdapter arrayAdapter) {
        this.arrayAdapter = arrayAdapter;
    }

    public String getStrSumCosts() {
        return strSumCosts;
    }

    public ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<String>();
        data.add(String.valueOf(items));
        data.add(strSumCosts);
        data.add(flat);

        return data;
    }

}
