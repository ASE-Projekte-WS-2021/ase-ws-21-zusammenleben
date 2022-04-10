package Presenter.BasketList;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;

import Entities.Basket;
import Entities.Flat;
import Entities.ShoppingItem;
import Model.BasketListModel;

public class BasketListPresenter implements BasketListContract.Presenter, BasketListContract.onBasketSuccessListener{

    private BasketListContract.onBasketSuccessListener mOnBasketSuccessListener;
    private BasketListModel basketListModel;
    private BasketListContract.View basketListView;
    Flat currentUserFlat;
    String day, currentUser;
    ArrayList<Basket> baskets;
    ArrayList<ArrayList<String>> basketList = new ArrayList<>();


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
        ArrayList<ShoppingItem> ShoppingItem = new ArrayList();
        Basket basket = new Basket(day, currentUser, flat, basketID, ShoppingItem);
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
        Log.d("debug7", "presenter empfängt und delegiert");
        for(int i = 0 ; i < baskets.size() ; i++){
            String title = baskets.get(i).getTitle();
            String creator = baskets.get(i).getCurrentUser();
            ArrayList<String> arrList = new ArrayList<>();
            arrList.add(title);
            arrList.add(creator);
            basketList.add(arrList);
        }
        Log.d("debug8", "jetzt gehts an den view zurück");
        basketListView.onBasketItemFound(basketList);
    }

    @Override
    public void retrieveBaskets(String id){
        Log.d("debug4", "von presenter zu model");
        baskets = basketListModel.retrieveBasketsFromFirebase(currentUserFlat.getId());


    }
}
