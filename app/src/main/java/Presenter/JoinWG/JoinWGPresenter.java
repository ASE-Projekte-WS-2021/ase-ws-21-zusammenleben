package Presenter.JoinWG;

import android.util.Log;

import java.util.ArrayList;

import Entities.WG;
import Model.JoinWGModel;

public class JoinWGPresenter implements JoinWGContract.Presenter, JoinWGContract.onJoinWGListener {

    private JoinWGModel mJoinWGModel;
    private JoinWGContract.View mJoinWGView;
    private WG foundWG;
    private ArrayList<WG> wgs = new ArrayList<>();
    String flatKey;

    public JoinWGPresenter(JoinWGContract.View joinWGView){
        this.mJoinWGView = joinWGView;
        mJoinWGModel = new JoinWGModel(this);
    }

    @Override
    public void retrieveFlat(String flatPassword) {
        flatKey = flatPassword;
        mJoinWGModel.retrieveFlatFromFirebase(flatPassword);
    }

    @Override
    public WG sendFlat() {
        return foundWG;
    }

    @Override
    public void addUserToFlat(String email, String flatID) {
        mJoinWGModel.addUserToFlatInFirebase(email, flatID);
    }

    @Override
    public void onSuccess(ArrayList<WG> wgs) {
        Log.d("given", flatKey);
        for(int i = 0 ; i < wgs.size() ; i++) {
            WG currentWG = wgs.get(i);
            String currentID = currentWG.getId();
            if(currentID.equals(flatKey)){
                String foundID = currentWG.getId();
                String foundOwner = currentWG.getMembers().get(0);
                int foundSize = currentWG.getSize();
                mJoinWGView.onWGFound(foundID, foundOwner, foundSize);
            }
        }
    }

    @Override
    public void onFailure(String message) {

    }
}


// todo foundflat address found flat name. Ist es jetzt der Name oder die Adresse?

// der rückwärtsweg fehlt noch - onWGNOTFOUND - error handling skippe ich gerade ziemlich - keine zeit