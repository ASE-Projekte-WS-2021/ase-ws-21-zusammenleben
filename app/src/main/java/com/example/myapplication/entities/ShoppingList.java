package com.example.myapplication.entities;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ShoppingList {

    private ArrayList<String> items;
    private ArrayList<String> costs;
    ArrayAdapter arrayAdapter;
    private String strSumCosts;
    private String flat;
    private String inputNameShoppingList;

    public ShoppingList () {
    }

    public ShoppingList (ArrayList<String> items,ArrayList<String> costs, String strSumCosts, String flat, String inputNameShoppingList) {
        this.items = items;
        this.costs = costs;
        this.strSumCosts = strSumCosts;
        this.flat = flat;
        this.inputNameShoppingList = inputNameShoppingList;

    }

    public ArrayList<String> getCosts() {
        return costs;
    }

    public void setCosts(ArrayList<String> costs) {
        this.costs = costs;
    }

    public String getInputNameShoppingList() {
        return inputNameShoppingList;
    }

    public void setInputNameShoppingList(String inputNameShoppingList) {
        this.inputNameShoppingList = inputNameShoppingList;
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
        data.add(String.valueOf(costs));
        data.add(strSumCosts);
        data.add(flat);
        data.add(inputNameShoppingList);

        return data;
    }

}
