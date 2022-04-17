package Entities;
import java.util.HashMap;

public class Basket {
    String title;
    String currentUser;
    String flatID;
    String basketID;
    HashMap<String, ShoppingItem> shoppingList;

    public Basket(String title, String currentUser, String flatID, String basketID, HashMap<String, ShoppingItem> shoppingList) {
        this.title = title;
        this.currentUser = currentUser;
        this.flatID = flatID;
        this.basketID = basketID;
        this.shoppingList = shoppingList;
    }

    // default empty constructor (required when working with Firebase)
    public Basket(){}

    public HashMap<String, ShoppingItem> getShoppingList(){
        return shoppingList;
    }

    public void setList(HashMap<String, ShoppingItem> shoppingList){
        this.shoppingList = shoppingList;
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
