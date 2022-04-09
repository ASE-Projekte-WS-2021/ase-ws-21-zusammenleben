package Presenter.BasketList;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

import Entities.Basket;
import Entities.Flat;
import Model.BasketListModel;

public class BasketListPresenter implements BasketListContract.Presenter, BasketListContract.onBasketSuccessListener{

    private BasketListContract.onBasketSuccessListener mOnBasketSuccessListener;
    private BasketListModel basketListModel;
    private BasketListContract.View basketListView;
    Flat currentUserFlat;
    String day;

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
    public void createBasket(String mail){
        day = LocalDate.now().getDayOfWeek().name();
        Basket basket = new Basket();
        Log.d("123test", day);

        basketListModel.addBasketToFirebase(basket);
    }

    @Override
    public void onBasketSucces() {

    }
}
