package Presenter.BasketList;
import android.os.Build;
import androidx.annotation.RequiresApi;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import Entities.Basket;
import Entities.Flat;
import Entities.ShoppingItem;
import Model.BasketListModel;

public class BasketListPresenter implements BasketListContract.Presenter, BasketListContract.onBasketSuccessListener{

    // MVP components
    private BasketListModel basketListModel;
    private BasketListContract.View basketListView;

    // Utils
    Flat currentUserFlat;
    String day, currentUser;
    ArrayList<Basket> baskets;
    ArrayList<ArrayList<String>> basketElements = new ArrayList<>();
    ArrayList<String> basketIDs = new ArrayList<>();

    public BasketListPresenter(BasketListContract.View basketListView){
        this.basketListView = basketListView;
        basketListModel = new BasketListModel(this);
    }

    // Presenter -> Model
    @Override
    public void retrieveFlat(String email) {
        basketListModel.retrieveFlatFromFirebase(email);
    }

    // When Model is successful
    @Override
    public void onFlatFoundSuccess(Flat flat) {
        currentUserFlat = flat;
        basketListView.onFlatFound(currentUserFlat);
    }

    // This is a bit hacky - we create the object and initialize a HashMap to avoid null pointer
    // Later, we clean the HashMaps's first object and start Presenter -> Model
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void createBasket(String mail, String flatID){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        day = translateDay(sdf.format(d));
        currentUser = mail;
        String flat = flatID;
        String basketID = "";
        HashMap<String, ShoppingItem> shoppingItems = new HashMap<>();
        ShoppingItem item = new ShoppingItem();
        shoppingItems.put("x", item);
        Basket basket = new Basket(day, currentUser, flat, basketID, shoppingItems);
        basketListModel.addBasketToFirebase(basket);
    }

    // This is a bit hacky too. But even if it is hardcoded, it is clear what happens
    // There is no easy library, java-function, etc. to translate from english to german
    private String translateDay(String s){
        switch (s){
            case "Monday":
                s = "Montagsliste";
                break;
            case "Tuesday":
                s = "Dienstagsliste";
                break;
            case "Wednesday":
                s = "Mittwochsliste";
                break;
            case "Thursday":
                s = "Donnerstagsliste";
                break;
            case "Friday":
                s = "Freitagsliste";
                break;
            case "Saturday":
                s = "Samstagsliste";
                break;
            case "Sunday":
                s = "Sonntagsliste";
                break;
        }
        return s;
    }

    // After Model is finished
    @Override
    public void onBasketAddedSuccess() {
        baskets = basketListModel.retrieveBasketsFromFirebase(currentUserFlat.getId());
    }

    // After Model is finished
    @Override
    public void onBasketsRetrieved(ArrayList<Basket> baskets) {
        for(int i = 0 ; i < baskets.size() ; i++){
            String title = baskets.get(i).getTitle();
            String creator = baskets.get(i).getCurrentUser();
            String basketID = baskets.get(i).getBasketID();
            ArrayList<String> basketElement = new ArrayList<>();
            basketElement.add(title);
            basketElement.add(creator);
            basketIDs.add(basketID);
            basketElements.add(basketElement);
        }
        // Presenter -> View
        basketListView.onBasketItemFound(basketElements, basketIDs);
    }

    // Presenter -> Model
    @Override
    public void retrieveBaskets(String id){
        baskets = basketListModel.retrieveBasketsFromFirebase(currentUserFlat.getId());
    }

    @Override
    public void deleteBasket(String id){
        basketListModel.deleteBasketFromFirebase(id);
    }

    // interface method
    @Override
    public void saveBasket(Basket basket) {
    }

}
