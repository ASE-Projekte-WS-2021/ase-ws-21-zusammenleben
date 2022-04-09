package Model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import Entities.Basket;
import Entities.Flat;
import Presenter.BasketList.BasketListContract;

public class BasketListModel implements BasketListContract.Model, BasketListContract.onBasketSuccessListener {

    private BasketListContract.onBasketSuccessListener mOnBasketSuccessListener;
    private FirebaseDatabase database = FirebaseDatabase.getInstance(FIREBASEPATH);
    private DatabaseReference refPayment = database.getReference(BASKETPATH);
    private DatabaseReference refFlat = database.getReference(FLATPATH);
    private static final String FIREBASEPATH = "https://wgfinance-b594f-default-rtdb.europe-west1.firebasedatabase.app/";
    private static final String BASKETPATH = "Baskets";
    private static final String FLATPATH = "WG";
    Flat retrievedFlat;

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

    }

    @Override
    public void onFlatFoundSuccess(Flat flat) {

    }

    @Override
    public void onBasketSucces() {

    }
}
