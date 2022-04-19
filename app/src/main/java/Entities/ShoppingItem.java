package Entities;

public class ShoppingItem {

    String itemName, itemQuantity, shoppingItemId;

    public ShoppingItem(String itemName, String itemQuantity, String shoppingItemId){
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.shoppingItemId = shoppingItemId;
    }

    // default empty constructor (required when working with Firebase)
    public ShoppingItem(){}

    public String getItemName(){
        return itemName;
    }

    public String getItemQuantity(){
        return itemQuantity;
    }

    public String getShoppingItemId (){ return shoppingItemId; }

    public void setShoppingItemId (String shoppingItemId) {this.shoppingItemId = shoppingItemId;}

}
