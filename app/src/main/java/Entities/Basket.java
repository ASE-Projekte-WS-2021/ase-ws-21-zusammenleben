package Entities;

import java.util.ArrayList;

public class Basket {
    String title;
    String currentUser;
    String timestamp;
    String flatID;
    String basketID;
    ArrayList<ShoppingList> list;

    public Basket(String title, String currentUser, String flatID, String basketID, ArrayList<ShoppingList> list) {
        this.title = title;
        this.currentUser = currentUser;
        this.flatID = flatID;
        this.basketID = basketID;
        this.list = list;
    }

    public Basket(){
    }

    public ArrayList<ShoppingList> getList(){
        return list;
    }

    public void setList(ArrayList<ShoppingList> newList){
        this.list = newList;
    }

    public String getBasketID(){
        return basketID;
    }

    public void setBasketID(String id){
        this.basketID = id;
    }

    public String getFlatID(){
        return flatID;
    }

    public String getTitle() {
        return title;
    }

    public String getCurrentUser() {
        return currentUser;
    }

}
