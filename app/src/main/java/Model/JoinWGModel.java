package Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Entities.WG;
import Presenter.JoinWG.JoinWGContract;

public class JoinWGModel implements JoinWGContract.Model, JoinWGContract.onJoinWGListener {

    private JoinWGContract.onJoinWGListener onJoinWGListener;
    private FirebaseDatabase database = FirebaseDatabase.getInstance(FIREBASEPATH);
    private DatabaseReference refWG = database.getReference(WGPATH);
    private static final String FIREBASEPATH = "https://wgfinance-b594f-default-rtdb.europe-west1.firebasedatabase.app/";
    private static final String WGPATH = "WG";
    WG foundWG;
    ArrayList<WG> wgs = new ArrayList<>();
    ArrayList<WG> retrievedList = new ArrayList<>();


    public JoinWGModel(JoinWGContract.onJoinWGListener onJoinWGListener){
        this.onJoinWGListener = onJoinWGListener;
    }

    @Override
    public ArrayList<WG> retrieveFlatFromFirebase(String flatPassword) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    String id = snap.getValue(WG.class).getId();
                    int size = snap.getValue(WG.class).getSize();
                    List<String> members = snap.getValue(WG.class).getMembers();
                    String address = snap.getValue(WG.class).getAddress();
                    WG wg = new WG(address, id, members, size);
                    wgs.add(wg);
                }
                onJoinWGListener.onSuccess(wgs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onJoinWGListener.onFailure(error.toString());
            }
        };
        refWG.addListenerForSingleValueEvent(valueEventListener);
        return wgs;
    }

    @Override
    public void addUserToFlatInFirebase(String email, String flatID) {
        Log.d("arrived", email+flatID);
        refWG.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    if(snap.getKey().equals(flatID)){
                        String address = snap.getValue(WG.class).getAddress();
                        List<String> members = snap.getValue(WG.class).getMembers();
                        int size = snap.getValue(WG.class).getSize();
                        members.add(email);
                        WG newPersonAddedWG = new WG(address, flatID, members, size);
                        refWG.child(flatID).setValue(newPersonAddedWG);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onJoinWGListener.onFailure(error.toString());
            }
        });
    }

    @Override
    public void onSuccess(ArrayList<WG> wgs) {

    }

    @Override
    public void onFailure(String message) {

    }
}
