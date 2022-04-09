package Presenter.BasketList;

import Entities.Basket;
import Entities.Flat;

public interface BasketListContract {

    interface View {
        void onFlatFound(Flat flat);

        void onBasketItemFound(Basket basket);

        void startIntent();
    }

    interface Presenter {
        void retrieveFlat(String email);
        void createBasket(String mail);
        void saveBasket(Basket basket);
    }

    interface Model {
        Flat retrieveFlatFromFirebase(String email);
        void addBasketToFirebase(Basket basket);
    }

    interface onBasketSuccessListener {
        void onFlatFoundSuccess(Flat flat);

        void onBasketSucces();
    }

}
