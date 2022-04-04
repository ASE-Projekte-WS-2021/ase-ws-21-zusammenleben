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

import Entities.Flat;
import Presenter.JoinFlat.JoinFlatContract;

public class JoinFlatModel implements JoinFlatContract.Model, JoinFlatContract.onJoinFlatListener {

    private JoinFlatContract.onJoinFlatListener onJoinFlatListener;
    private FirebaseDatabase database = FirebaseDatabase.getInstance(FIREBASEPATH);
    private DatabaseReference refFlat = database.getReference(FLATPATH);
    private static final String FIREBASEPATH = "https://wgfinance-b594f-default-rtdb.europe-west1.firebasedatabase.app/";
    private static final String FLATPATH = "WG";
    Flat foundFlat;
    ArrayList<Flat> flats = new ArrayList<>();
    ArrayList<Flat> retrievedList = new ArrayList<>();


    public JoinFlatModel(JoinFlatContract.onJoinFlatListener onJoinFlatListener){
        this.onJoinFlatListener = onJoinFlatListener;
    }

    @Override
    public ArrayList<Flat> retrieveFlatFromFirebase(String flatPassword) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    String id = snap.getValue(Flat.class).getId();
                    int size = snap.getValue(Flat.class).getSize();
                    List<String> members = snap.getValue(Flat.class).getMembers();
                    String address = snap.getValue(Flat.class).getAddress();
                    Flat flat = new Flat(address, id, members, size);
                    flats.add(flat);
                }
                onJoinFlatListener.onSuccess(flats);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onJoinFlatListener.onFailure(error.toString());
            }
        };
        refFlat.addListenerForSingleValueEvent(valueEventListener);
        return flats;
    }

    @Override
    public void addUserToFlatInFirebase(String email, String flatID) {
        Log.d("arrived", email+flatID);
        refFlat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    if(snap.getKey().equals(flatID)){
                        String address = snap.getValue(Flat.class).getAddress();
                        List<String> members = snap.getValue(Flat.class).getMembers();
                        int size = snap.getValue(Flat.class).getSize();
                        members.add(email);
                        Flat newPersonAddedFlat = new Flat(address, flatID, members, size);
                        refFlat.child(flatID).setValue(newPersonAddedFlat);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onJoinFlatListener.onFailure(error.toString());
            }
        });
    }

    @Override
    public void onSuccess(ArrayList<Flat> flats) {

    }

    @Override
    public void onFailure(String message) {

    }
}
