package Presenter.ShoppingList;

import java.util.ArrayList;

import Entities.Basket;
import Entities.ShoppingItem;

public interface ShoppingListContract {

    interface View{
        void onBasketItemRetrieved(Basket basket);
        void onShoppingItemAdded(ArrayList<ArrayList<String>> shoppingItems, ArrayList<String> shoppingItemIDs);
    }

    interface Presenter{
        void retrieveBasketItem(String basketID);
        void onBasketItemRetrieved(Basket basket);
        void addShoppingItem(String basketID, ShoppingItem shoppingItemId);
        void deleteShoppingListItem (String shoppingItemId);
    }

    interface Model{
        Basket retrieveBasketItemFromFirebase(String basketID);
        void addShoppingItemToFirebase(String basketID, ShoppingItem shoppingItemId);
        ArrayList<ShoppingItem> retrieveShoppingItemFromFirebase(String shoppingItemId);
        void deleteItemFromFirebase (String shoppingItemId);
    }

    interface onShoppingSuccessListener{
        void onBasketItemRetrieved(Basket basket);
        void onShoppingItemRetrieved(ArrayList<ShoppingItem> shoppingList);
        void onShoppingItemAdded(String shoppingItemId);
        void onShoppingListItemAltered (String shoppingItemId);
    }
}


//ShoppingItemDelete, ShoppingItemEdit
//Darstellung über Dialog in View
//Altered soll delete und edit handln