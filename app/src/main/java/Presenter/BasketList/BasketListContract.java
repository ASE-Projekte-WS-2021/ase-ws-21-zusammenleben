package Presenter.BasketList;

import java.util.ArrayList;

import Entities.Basket;
import Entities.Flat;

public interface BasketListContract {

    interface View {
        void onFlatFound(Flat flat);

        void onBasketItemFound(ArrayList<ArrayList<String>> basketList);

        void startIntent();
    }

    interface Presenter {
        void retrieveFlat(String email);
        void createBasket(String mail, String flatID);
        void retrieveBaskets(String id);
        void saveBasket(Basket basket);
    }

    interface Model {
        Flat retrieveFlatFromFirebase(String email);
        void addBasketToFirebase(Basket basket);
        ArrayList<Basket> retrieveBasketsFromFirebase(String flatID);
    }

    interface onBasketSuccessListener {
        void onFlatFoundSuccess(Flat flat);
        void onBasketAddedSuccess();
        void onBasketsRetrieved(ArrayList<Basket> baskets);
    }

}
