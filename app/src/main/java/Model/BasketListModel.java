package Model;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import Entities.Basket;
import Entities.Flat;
import Entities.ShoppingItem;
import Presenter.BasketList.BasketListContract;

public class BasketListModel implements BasketListContract.Model, BasketListContract.onBasketSuccessListener {

    // MVP components
    private BasketListContract.onBasketSuccessListener mOnBasketSuccessListener;

    // Firebase
    private FirebaseDatabase database = FirebaseDatabase.getInstance(FIREBASEPATH);
    private DatabaseReference refFlat = database.getReference(FLATPATH);
    private DatabaseReference refBasket = database.getReference(BASKETPATH);

    // Utils
    private static final String FIREBASEPATH = "https://wgfinance-b594f-default-rtdb.europe-west1.firebasedatabase.app/";
    private static final String BASKETPATH = "Baskets";
    private static final String FLATPATH = "WG";
    ArrayList<Basket> baskets = new ArrayList<>();
    Flat retrievedFlat;

    public BasketListModel(BasketListContract.onBasketSuccessListener onBasketSuccessListener){
        this.mOnBasketSuccessListener = onBasketSuccessListener;
    }

    // Model -> Firebase
    // Filter with email, create object from Firebase data
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
                // wait, when finished, go back to Presenter
                mOnBasketSuccessListener.onFlatFoundSuccess(retrievedFlat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        refFlat.addListenerForSingleValueEvent(valueEventListener);
        return retrievedFlat;
    }

    // Model -> Firebase
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

    // After fetching the flat, get all baskets with flat
    // This procedure with objects and inner snapshot iteration is described in ShoppingListModel.java
    @Override
    public ArrayList<Basket> retrieveBasketsFromFirebase(String flatID) {
        refBasket.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    if(snap.getValue().toString().contains(flatID)){
                        List<ShoppingItem> shoppingItems = new ArrayList<>();
                        DataSnapshot innerList = snap.child("shoppingList");
                        // Inside the max depth of Firebase tree, you can use the object directly
                        for(DataSnapshot inner : innerList.getChildren()){
                            ShoppingItem innerShoppingItem = inner.getValue(ShoppingItem.class);
                            shoppingItems.add(innerShoppingItem);
                        }

                        // User objects, then convert it to strings
                        Object innerTitle = snap.child("title").getValue();
                        Object innerBasketID = snap.child("basketID").getValue();
                        Object innerCurrentUser = snap.child("currentUser").getValue();

                        Basket basket = new Basket(innerTitle.toString(),
                                innerCurrentUser.toString(),
                                flatID, innerBasketID.toString(),
                                (ArrayList<ShoppingItem>) shoppingItems);

                        baskets.add(basket);
                    }
                }
                // wait, when finished -> back to Presenter
                mOnBasketSuccessListener.onBasketsRetrieved(baskets);
                baskets.clear();
            }
        });
        return baskets;
    }

    // Model -> Firebase
    @Override
    public void deleteBasketFromFirebase (String basketId){
        refBasket.child(basketId).removeValue();
    }

    // interface methods
    @Override
    public void onFlatFoundSuccess(Flat flat) {}

    @Override
    public void onBasketAddedSuccess() {}

    @Override
    public void onBasketsRetrieved(ArrayList<Basket> baskets) {}

}
