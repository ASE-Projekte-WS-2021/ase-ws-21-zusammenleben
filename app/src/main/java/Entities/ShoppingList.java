package Entities;

import java.util.ArrayList;

public class ShoppingList {

    private ArrayList<String> items;
    private ArrayList<String> costs;
    private String strSumCosts;
    private String flat;
    private String inputNameShoppingList;

    public ShoppingList() {
    }

    public ShoppingList(ArrayList<String> items, ArrayList<String> costs, String strSumCosts, String flat, String inputNameShoppingList) {
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

    public void setStrSumCosts(String strSumCosts) {
        this.strSumCosts = strSumCosts;
    }

    public String getStrSumCosts() {
        return strSumCosts;
    }
}
