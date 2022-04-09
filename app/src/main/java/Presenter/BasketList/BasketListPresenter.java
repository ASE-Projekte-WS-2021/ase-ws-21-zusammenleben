package Presenter.BasketList;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;

import Entities.Basket;
import Entities.Flat;
import Entities.ShoppingList;
import Model.BasketListModel;

public class BasketListPresenter implements BasketListContract.Presenter, BasketListContract.onBasketSuccessListener{

    private BasketListContract.onBasketSuccessListener mOnBasketSuccessListener;
    private BasketListModel basketListModel;
    private BasketListContract.View basketListView;
    Flat currentUserFlat;
    String day, currentUser;
    ArrayList<Basket> baskets;

    public BasketListPresenter(BasketListContract.View basketListView){
        this.basketListView = basketListView;
        basketListModel = new BasketListModel(this);
    }


    @Override
    public void retrieveFlat(String email) {
        basketListModel.retrieveFlatFromFirebase(email);
    }

    @Override
    public void saveBasket(Basket basket) {

    }

    @Override
    public void onFlatFoundSuccess(Flat flat) {
        currentUserFlat = flat;
        basketListView.onFlatFound(currentUserFlat);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void createBasket(String mail, String flatID){
        day = translateDay(LocalDate.now().getDayOfWeek().name());
        currentUser = mail;
        String flat = flatID;
        String basketID = "";
        ArrayList<ShoppingList> shoppingList = new ArrayList();
        Basket basket = new Basket(day, currentUser, flat, basketID, shoppingList);
        basketListModel.addBasketToFirebase(basket);
    }

    private String translateDay(String s){
        switch (s){
            case "MONDAY":
                s = "Montagsliste";

            case "TUESDAY":
                s = "Dienstagsliste";

            case "WEDNESDAY":
                s = "Mittwochsliste";

            case "THURSDAY":
                s = "Donnerstagsliste";

            case "FRIDAY":
                s = "Freitagsliste";

            case "SATURDAY":
                s = "Samstagsliste";

            case "SUNDAY":
                s = "Sonntagsliste";
        }
        return s;
    }

    @Override
    public void onBasketAddedSuccess() {
        baskets = basketListModel.retrieveBasketsFromFirebase(currentUserFlat.getId());
    }

    @Override
    public void onBasketsRetrieved(ArrayList<Basket> baskets) {
        basketListView.onBasketItemFound(baskets);
    }
}
