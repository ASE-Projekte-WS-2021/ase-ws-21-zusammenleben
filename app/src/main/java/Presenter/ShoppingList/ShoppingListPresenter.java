package Presenter.ShoppingList;

import java.util.ArrayList;

import Entities.Basket;
import Entities.ShoppingItem;
import Model.ShoppingListModel;

public class ShoppingListPresenter implements ShoppingListContract.Presenter, ShoppingListContract.onShoppingSuccessListener {

    // MVP components
    private ShoppingListModel shoppingListModel;
    private ShoppingListContract.View shoppingListView;

    // Utils
    Basket retrievedBasket;
    ArrayList<ArrayList<String>> shoppingItems = new ArrayList<>();
    ArrayList<String> shoppingItemIDs = new ArrayList<>();

    public ShoppingListPresenter(ShoppingListContract.View shoppingListView){
        this.shoppingListView = shoppingListView;
        shoppingListModel = new ShoppingListModel(this);
    }

    // init communication Presenter -> Model
    @Override
    public void retrieveBasketItem(String basketID) {
        shoppingListModel.retrieveBasketItemFromFirebase(basketID);
    }

    // result from Model, new communication Presenter -> Model
    @Override
    public void onBasketItemRetrieved(Basket basket) {
        retrievedBasket = basket;
        shoppingListModel.retrieveShoppingItemFromFirebase(basket.getBasketID());
    }

    // Presenter -> Model
    @Override
    public void addShoppingItem(String basketID, ShoppingItem item) {
        shoppingListModel.addShoppingItemToFirebase(basketID, item);
    }

    // Presenter -> Model
    @Override
    public void deleteShoppingListItem(String itemID, String basketID) {
        shoppingListModel.deleteItemFromFirebase(itemID, basketID);
    }


    // Unpack and prepare data from Model
    @Override
    public void onShoppingItemRetrieved(ArrayList<ShoppingItem> shoppingList) {
        for(int i = 0 ; i < shoppingList.size() ; i++){
            String itemName = shoppingList.get(i).getItemName();
            String itemQuantity = String.valueOf(shoppingList.get(i).getItemQuantity());
            String shoppingItemID = shoppingList.get(i).getShoppingItemId();
            ArrayList<String> shoppingItem = new ArrayList<>();
            shoppingItem.add(itemName);
            shoppingItem.add(itemQuantity);
            shoppingItemIDs.add(shoppingItemID);
            shoppingItems.add(shoppingItem);
        }
        // communication Presenter -> View
        shoppingListView.onShoppingItemAdded(shoppingItems, shoppingItemIDs);
    }

    // interface method
    @Override
    public void onShoppingItemAdded(String basketID) {}

    // interface method
    @Override
    public void onShoppingListItemAltered(String itemID) {}

    // interface method
    @Override
    public void onShoppingItemDeleted(ShoppingItem item) {}
}
