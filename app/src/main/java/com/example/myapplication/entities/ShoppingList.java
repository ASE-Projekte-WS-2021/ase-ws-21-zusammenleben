package com.example.myapplication.entities;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ShoppingList {

    private ArrayList<String> items;
    ArrayAdapter arrayAdapter;
    private String strSumCosts;

    public ShoppingList (ArrayList<String> items, String strSumCosts) {
        this.items = items;
        this.strSumCosts = strSumCosts;

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

}
