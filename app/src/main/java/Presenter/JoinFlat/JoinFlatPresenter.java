package Presenter.JoinFlat;

import android.util.Log;

import java.util.ArrayList;

import Entities.Flat;
import Model.JoinFlatModel;

public class JoinFlatPresenter implements JoinFlatContract.Presenter, JoinFlatContract.onJoinFlatListener {

    private JoinFlatModel mJoinFlatModel;
    private JoinFlatContract.View mJoinFlatView;
    private Flat foundFlat;
    private ArrayList<Flat> flats = new ArrayList<>();
    String flatKey;

    public JoinFlatPresenter(JoinFlatContract.View joinFlatView){
        this.mJoinFlatView = joinFlatView;
        mJoinFlatModel = new JoinFlatModel(this);
    }

    @Override
    public void retrieveFlat(String flatPassword) {
        flatKey = flatPassword;
        mJoinFlatModel.retrieveFlatFromFirebase(flatPassword);
    }

    @Override
    public Flat sendFlat() {
        return foundFlat;
    }

    @Override
    public void addUserToFlat(String email, String flatID) {
        mJoinFlatModel.addUserToFlatInFirebase(email, flatID);
    }

    @Override
    public void onSuccess(ArrayList<Flat> flats) {
        Log.d("given", flatKey);
        for(int i = 0; i < flats.size() ; i++) {
            Flat currentFlat = flats.get(i);
            String currentID = currentFlat.getId();
            if(currentID.equals(flatKey)){
                String foundID = currentFlat.getId();
                String foundOwner = currentFlat.getMembers().get(0);
                int foundSize = currentFlat.getSize();
                mJoinFlatView.onFlatFound(foundID, foundOwner, foundSize);
            }
        }
    }

    @Override
    public void onFailure(String message) {

    }
}


// todo foundflat address found flat name. Ist es jetzt der Name oder die Adresse?

// der rückwärtsweg fehlt noch - onWGNOTFOUND - error handling skippe ich gerade ziemlich - keine zeit