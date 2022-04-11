package Entities;

public class ShoppingItem {

    private String itemName;
    private int itemQuantity;

    String shoppingItemId;

    public ShoppingItem(String itemName, int itemQuantity, String shoppingItemId){
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.shoppingItemId = shoppingItemId;
    }

    public ShoppingItem(){}

    public String getItemName(){
        return itemName;
    }

    public int getItemQuantity(){
        return itemQuantity;
    }

    public String getShoppingItemId (){ return shoppingItemId; }

    public void setShoppingItemId (String shoppingItemId) {this.shoppingItemId = shoppingItemId;}

}
