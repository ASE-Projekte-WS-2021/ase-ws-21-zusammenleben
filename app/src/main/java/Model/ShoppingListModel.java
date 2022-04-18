package Model;
import android.util.Log;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Entities.Basket;
import Entities.ShoppingItem;
import Presenter.ShoppingList.ShoppingListContract;

public class ShoppingListModel implements ShoppingListContract.Model, ShoppingListContract.onShoppingSuccessListener {

    // MVP components
    private ShoppingListContract.onShoppingSuccessListener mOnShoppingSuccessListener;

    // Firebase
    private FirebaseDatabase database = FirebaseDatabase.getInstance(FIREBASEPATH);
    private DatabaseReference refBasket = database.getReference(BASKETPATH);

    // Utils
    private static final String FIREBASEPATH = "https://wgfinance-b594f-default-rtdb.europe-west1.firebasedatabase.app/";
    private static final String BASKETPATH = "Baskets";
    Basket retrievedBasket;
    ArrayList<ShoppingItem> shoppingList = new ArrayList<>();
    ArrayList<ShoppingItem> retrievedShoppingList = new ArrayList<>();
    String shoppingItemId;

    public ShoppingListModel(ShoppingListContract.onShoppingSuccessListener onShoppingSuccessListener){
        this.mOnShoppingSuccessListener = onShoppingSuccessListener;
    }


    // Model -> Firebase. This looks bulky but it is the same procedure as in every other Firebase Query
    @Override
    public Basket retrieveBasketItemFromFirebase(String basketID) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    if(snap.getValue().toString().contains(basketID)){
                        Object innerCurrentUser = snap.child("currentUser").getValue();
                        Object innerFlatID = snap.child("flatID").getValue();
                        Object innerTitle = snap.child("title").getValue();
                        List<ShoppingItem> shoppingItems = new ArrayList<>();
                        DataSnapshot innerList = snap.child("shoppingList");
                        for(DataSnapshot inner : innerList.getChildren()){
                            ShoppingItem innerShoppingItem = inner.getValue(ShoppingItem.class);
                            shoppingItems.add(innerShoppingItem);
                        }

                        retrievedBasket = new Basket(innerTitle.toString(), innerCurrentUser.toString(), innerFlatID.toString(), basketID, (ArrayList<ShoppingItem>) shoppingItems);
                    }
                }
                mOnShoppingSuccessListener.onBasketItemRetrieved(retrievedBasket);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        refBasket.addValueEventListener(valueEventListener);
        return retrievedBasket;
    }

    // Model -> Firebase. But do it with a callback to avoid time conflicts
    @Override
    public void addShoppingItemToFirebase(String basketID, ShoppingItem item) {
        refBasket.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(@NonNull DataSnapshot dataSnapshot) {
                DatabaseReference reference = refBasket.child(basketID).child("shoppingList").push();
                String uniqueFirebaseID = reference.getKey();
                item.setShoppingItemId(uniqueFirebaseID);
                reference.setValue(item);
                shoppingItemId = item.getShoppingItemId();
                mOnShoppingSuccessListener.onShoppingItemAdded(shoppingItemId);
            }
        });
    }

    // Model -> Firebase. You need to filter the data with the basketID and create a new object
    @Override
    public ArrayList<ShoppingItem> retrieveShoppingItemsFromFirebase(String basketID) {
        refBasket.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()) {
                    if(snap.getValue().toString().contains(basketID)){
                        shoppingList = new ArrayList<>();
                        DataSnapshot innerList = snap.child("shoppingList");
                        for(DataSnapshot inner : innerList.getChildren()){
                            ShoppingItem innerShoppingItem = inner.getValue(ShoppingItem.class);
                            shoppingList.add(innerShoppingItem);
                        }
                    }
                }
                Log.d("122model", String.valueOf(shoppingList.size()));
                Log.d("123model", shoppingList.toString());
                // Callback Model -> Presenter
                mOnShoppingSuccessListener.onShoppingItemRetrieved(shoppingList);
                shoppingList.clear();
            }
        });
        return shoppingList;
    }

    // Model -> Firebase
    @Override
    public void deleteItemFromFirebase(String itemId, String basketId) {
        refBasket.child(basketId).child("shoppingList").child(itemId).removeValue();
    }

    @Override
    public void deleteFirstItemInFirebase(String basketID){
        refBasket.child(basketID).child("shoppingList").child("x").removeValue();
    }

    // interface methods
    @Override
    public void onBasketItemRetrieved(Basket basket) {}

    @Override
    public void onShoppingItemRetrieved(ArrayList<ShoppingItem> shoppingList) {}

    @Override
    public void onShoppingItemAdded(String basketID) {}

    @Override
    public void onShoppingListItemAltered(String itemID) {}

    @Override
    public void onShoppingItemDeleted(ShoppingItem item) {}
}
