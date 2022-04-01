package Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.security.auth.callback.Callback;

import Entities.WG;
import Presenter.AddWG.AddWGContract;

public class AddWGModel implements AddWGContract.Model, AddWGContract.onAddWGListener {

    private AddWGContract.View mAddWGView;
    private AddWGContract.onAddWGListener mOnAddWGListener;

    private FirebaseDatabase database = FirebaseDatabase.getInstance(FIREBASEPATH);
    private DatabaseReference refWG;
    private static final String FIREBASEPATH = "https://wgfinance-b594f-default-rtdb.europe-west1.firebasedatabase.app/";
    private static final String WGPATH = "WG";

    public AddWGModel(AddWGContract.onAddWGListener onAddWGListener){
        this.mOnAddWGListener = onAddWGListener;
    }

    @Override
    public void addWGtoFirebase(WG wg) {
        refWG = database.getReference(WGPATH);
        refWG.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id = wg.getId();
                refWG.child(id).setValue(wg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("error occured", error.toString());
            }
        });
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFailure(String message) {

    }
}
