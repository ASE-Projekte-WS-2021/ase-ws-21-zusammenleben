package Entities;

public class ShoppingItem {

    private String itemName;
    private int itemQuantity;

    public ShoppingItem(String itemName, int itemQuantity){
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    public ShoppingItem(){}

    public String getItemName(){
        return itemName;
    }

    public int getItemQuantity(){
        return itemQuantity;
    }

}
