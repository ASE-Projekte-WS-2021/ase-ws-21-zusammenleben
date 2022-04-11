package Presenter.ShoppingList;

import java.util.ArrayList;

import Entities.Basket;
import Entities.ShoppingItem;

public interface ShoppingListContract {

    interface View{
        void onBasketItemRetrieved(Basket basket);
        void onShoppingItemAdded(ShoppingItem item);
    }

    interface Presenter{
        void retrieveBasketItem(String basketID);
        void onBasketItemRetrieved(Basket basket);
        void addShoppingItem(String basketID, ShoppingItem item);
    }

    interface Model{
        Basket retrieveBasketItemFromFirebase(String basketID);
        void addShoppingItemToFirebase(String basketID, ShoppingItem item);
        ArrayList<ShoppingItem> retrieveShoppingItemFromFirebase(String basketID);
    }

    interface onShoppingSuccessListener{
        void onBasketItemRetrieved(Basket basket);
        void onShoppingItemRetrieved(String basketID);
        void onShoppingItemAdded(String basketID);
    }
}
