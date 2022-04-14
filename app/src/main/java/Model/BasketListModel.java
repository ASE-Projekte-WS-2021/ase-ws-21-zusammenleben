package Model;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Entities.Basket;
import Entities.Flat;
import Entities.ShoppingItem;
import Presenter.BasketList.BasketListContract;
import androidx.annotation.NonNull;

public class BasketListModel implements BasketListContract.Model, BasketListContract.onBasketSuccessListener {

    private BasketListContract.onBasketSuccessListener mOnBasketSuccessListener;
    private FirebaseDatabase database = FirebaseDatabase.getInstance(FIREBASEPATH);
    private DatabaseReference refPayment = database.getReference(BASKETPATH);
    private DatabaseReference refFlat = database.getReference(FLATPATH);
    private DatabaseReference refBasket = database.getReference(BASKETPATH);
    private static final String FIREBASEPATH = "https://wgfinance-b594f-default-rtdb.europe-west1.firebasedatabase.app/";
    private static final String BASKETPATH = "Baskets";
    private static final String FLATPATH = "WG";
    Flat retrievedFlat;
    ArrayList<Basket> baskets = new ArrayList<>();

    public BasketListModel(BasketListContract.onBasketSuccessListener onBasketSuccessListener){
        this.mOnBasketSuccessListener = onBasketSuccessListener;
    }

    @Override
    public Flat retrieveFlatFromFirebase(String email) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    List<String> members = snap.getValue(Flat.class).getMembers();
                    if(members.contains(email)){
                        String address = snap.getValue(Flat.class).getAddress();
                        String id = snap.getValue(Flat.class).getId();
                        int size = members.size();
                        retrievedFlat = new Flat(address, id, members, size);
                    }
                }
                mOnBasketSuccessListener.onFlatFoundSuccess(retrievedFlat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        refFlat.addListenerForSingleValueEvent(valueEventListener);
        return retrievedFlat;
    }

    @Override
    public void addBasketToFirebase(Basket basket) {
        refBasket.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(@NonNull DataSnapshot dataSnapshot) {
                DatabaseReference reference = refBasket.push();
                String uniqueFirebaseID = reference.getKey();
                basket.setBasketID(uniqueFirebaseID);
                reference.setValue(basket);
                mOnBasketSuccessListener.onBasketAddedSuccess();
            }
        });
    }

    @Override
    public ArrayList<Basket> retrieveBasketsFromFirebase(String flatID) {
        refBasket.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    if(snap.getValue(Basket.class).getFlatID().equals(flatID)){
                        String title = snap.getValue(Basket.class).getTitle();
                        String currentUser = snap.getValue(Basket.class).getCurrentUser();
                        String basketID = snap.getValue(Basket.class).getBasketID();
                        HashMap<String, ShoppingItem> shoppingList = snap.getValue(Basket.class).getShoppingList();
                        Basket basket = new Basket(title, currentUser, flatID, basketID, shoppingList);
                        baskets.add(basket);
                    }
                }
                mOnBasketSuccessListener.onBasketsRetrieved(baskets);
                baskets.clear();
            }
        });
        return baskets;
    }

    @Override
    public void onFlatFoundSuccess(Flat flat) {

    }

    @Override
    public void onBasketAddedSuccess() {

    }

    @Override
    public void onBasketsRetrieved(ArrayList<Basket> baskets) {

    }
}
