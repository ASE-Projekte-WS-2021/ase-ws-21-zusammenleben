package Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Entities.Flat;
import Presenter.AddFlat.AddFlatContract;

public class AddFlatModel implements AddFlatContract.Model, AddFlatContract.onAddFlatListener {

    // MVP components
    private AddFlatContract.onAddFlatListener mOnAddFlatListener;

    // Firebase
    private FirebaseDatabase database = FirebaseDatabase.getInstance(FIREBASEPATH);
    private DatabaseReference refFlat;

    // Utils
    private static final String FIREBASEPATH = "https://wgfinance-b594f-default-rtdb.europe-west1.firebasedatabase.app/";
    private static final String FLATPATH = "WG";

    public AddFlatModel(AddFlatContract.onAddFlatListener onAddFlatListener){
        this.mOnAddFlatListener = onAddFlatListener;
    }

    // Model -> Firebase
    // In this case, a callback could be implemented, but is not necessary
    @Override
    public void addFlattoFirebase(Flat flat) {
        refFlat = database.getReference(FLATPATH);
        refFlat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id = flat.getId();
                refFlat.child(id).setValue(flat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("error occured", error.toString());
            }
        });
    }

    // interface methods
    @Override
    public void onSuccess(String message) {}

    @Override
    public void onFailure(String message) {}
}
