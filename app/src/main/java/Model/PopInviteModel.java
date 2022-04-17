package Model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import Entities.Flat;
import Presenter.PopInvite.PopInviteContract;

public class PopInviteModel implements PopInviteContract.Model, PopInviteContract.Listener {

    private PopInviteContract.Listener listener;
    private FirebaseDatabase database = FirebaseDatabase.getInstance(FIREBASEPATH);
    private DatabaseReference refFat = database.getReference(FLATPATH);
    private static final String FIREBASEPATH = "https://wgfinance-b594f-default-rtdb.europe-west1.firebasedatabase.app/";
    private static final String FLATPATH = "WG";
    String flatID;

    public PopInviteModel(PopInviteContract.Listener listener){
        this.listener = listener;
    }

    @Override
    public void getFlatIDFromFirebase(String email) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    List<String> members = snap.getValue(Flat.class).getMembers();
                    if(members.contains(email)){
                        flatID = snap.getValue(Flat.class).getId();
                    }
                }
                listener.onFlatIDRetrieved(flatID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        refFat.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public void onFlatIDRetrieved(String flatID) {

    }
}
