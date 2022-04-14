package Model;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Entities.Basket;
import Entities.ShoppingItem;
import Presenter.ShoppingList.ShoppingListContract;
import androidx.annotation.NonNull;

public class ShoppingListModel implements ShoppingListContract.Model, ShoppingListContract.onShoppingSuccessListener {

    private ShoppingListContract.onShoppingSuccessListener mOnShoppingSuccessListener;
    private FirebaseDatabase database = FirebaseDatabase.getInstance(FIREBASEPATH);
    private DatabaseReference refBasket = database.getReference(BASKETPATH);
    private static final String FIREBASEPATH = "https://wgfinance-b594f-default-rtdb.europe-west1.firebasedatabase.app/";
    private static final String BASKETPATH = "Baskets";
    Basket retrievedBasket;
    HashMap<String, ShoppingItem> shoppingItems = new HashMap<>();
    ArrayList<ShoppingItem> shoppingList = new ArrayList<>();

    String shoppingItemId;

    public ShoppingListModel(ShoppingListContract.onShoppingSuccessListener onShoppingSuccessListener){
        this.mOnShoppingSuccessListener = onShoppingSuccessListener;
    }


    @Override
    public Basket retrieveBasketItemFromFirebase(String basketID) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    if(snap.getValue(Basket.class).getBasketID().equals(basketID)){
                        String title = snap.getValue(Basket.class).getTitle();
                        String creator = snap.getValue(Basket.class).getCurrentUser();
                        String flatID = snap.getValue(Basket.class).getFlatID();
                        HashMap<String,ShoppingItem> shoppingItems = snap.getValue(Basket.class).getShoppingList();
                        retrievedBasket = new Basket(title, creator, flatID, basketID, shoppingItems);
                    }
                }

                mOnShoppingSuccessListener.onBasketItemRetrieved(retrievedBasket);
                Log.d("rutsche3", retrievedBasket.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };

        refBasket.addValueEventListener(valueEventListener);
        return retrievedBasket;
    }

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

    @Override
    public ArrayList<ShoppingItem> retrieveShoppingItemFromFirebase(String basketID) {
        Log.d("rutscheCheck", basketID);
        refBasket.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    if(snap.getValue(Basket.class).getBasketID().equals(basketID)){
                        shoppingItems = (HashMap<String, ShoppingItem>) snap.child("shoppingList").getValue();
                        Log.d("rutscheCheck1", shoppingItems.toString());
                        for(Map.Entry<String, ShoppingItem> e : shoppingItems.entrySet()) {
                            Log.d("rutscheCheck1.5", e.toString());
                            String shoppingItemID = e.getKey();
                            Log.d("rutscheCheck2",shoppingItemID.toString());
                            String x = snap.child("shoppingList").child(shoppingItemID).getValue().toString();
                            Log.d("rutscheX", x);
                            String itemName = snap.child("shoppingList").child(shoppingItemID).getValue(ShoppingItem.class).getItemName();
                            int itemQuantity = snap.child("shoppingList").child(shoppingItemID).getValue(ShoppingItem.class).getItemQuantity();
                            //String shoppingItemID = snap.child("shoppingList").getValue(ShoppingItem.class).getShoppingItemId();
                            ShoppingItem shoppingItem = new ShoppingItem(itemName, itemQuantity, shoppingItemID);
                            shoppingList.add(shoppingItem);
                        }
                    }
                }
                Log.d("rutsche5", shoppingList.toString());
                mOnShoppingSuccessListener.onShoppingItemRetrieved(shoppingList);
                //shoppingList.clear();
            }
        });
        return shoppingList;
    }

    @Override
    public void deleteItemFromFirebase(String itemID) {

    }

    @Override
    public void onBasketItemRetrieved(Basket basket) {

    }

    @Override
    public void onShoppingItemRetrieved(ArrayList<ShoppingItem> shoppingList) {

    }

    @Override
    public void onShoppingItemAdded(String basketID) {

    }

    @Override
    public void onShoppingListItemAltered(String itemID) {

    }
}
