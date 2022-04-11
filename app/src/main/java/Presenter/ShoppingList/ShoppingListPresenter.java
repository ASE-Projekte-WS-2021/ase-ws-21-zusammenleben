package Presenter.ShoppingList;

import android.util.Log;

import Entities.Basket;
import Entities.ShoppingItem;
import Model.ShoppingListModel;

public class ShoppingListPresenter implements ShoppingListContract.Presenter, ShoppingListContract.onShoppingSuccessListener {

    private ShoppingListContract.onShoppingSuccessListener mOnShoppingSuccessListener;
    private ShoppingListModel shoppingListModel;
    private ShoppingListContract.View shoppingListView;
    Basket retrievedBasket;

    public ShoppingListPresenter(ShoppingListContract.View shoppingListView){
        this.shoppingListView = shoppingListView;
        shoppingListModel = new ShoppingListModel(this);
    }

    @Override
    public void retrieveBasketItem(String basketID) {
        shoppingListModel.retrieveBasketItemFromFirebase(basketID);
    }

    @Override
    public void onBasketItemRetrieved(Basket basket) {
        retrievedBasket = basket;
        Log.d("check", retrievedBasket.getCurrentUser());
    }

    @Override
    public void addShoppingItem(String basketID, ShoppingItem item) {

    }

    @Override
    public void deleteShoppingListItem(String itemID) {

    }


    @Override
    public void onShoppingItemRetrieved(String basketID) {

    }

    @Override
    public void onShoppingItemAdded(String basketID) {

    }

    @Override
    public void onShoppingListItemAltered(String itemID) {

    }
}
