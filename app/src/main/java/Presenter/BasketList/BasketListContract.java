package Presenter.BasketList;

import java.util.ArrayList;

import Entities.Basket;
import Entities.Flat;

public interface BasketListContract {

    interface View {
        void onFlatFound(Flat flat);

        void onBasketItemFound(ArrayList<Basket> baskets);

        void startIntent();
    }

    interface Presenter {
        void retrieveFlat(String email);
        void createBasket(String mail, String flatID);
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
