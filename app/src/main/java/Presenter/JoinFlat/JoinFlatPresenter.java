package Presenter.JoinFlat;
import android.util.Log;
import java.util.ArrayList;
import Entities.Flat;
import Model.JoinFlatModel;

public class JoinFlatPresenter implements JoinFlatContract.Presenter, JoinFlatContract.onJoinFlatListener {

    // MVP components
    private JoinFlatModel mJoinFlatModel;
    private JoinFlatContract.View mJoinFlatView;

    // Util
    private String flatID;

    public JoinFlatPresenter(JoinFlatContract.View joinFlatView){
        this.mJoinFlatView = joinFlatView;
        mJoinFlatModel = new JoinFlatModel(this);
    }

    // Retrieve flatID, then Presenter -> Model
    @Override
    public void retrieveFlat(String flatPassword) {
        flatID = flatPassword;
        mJoinFlatModel.retrieveFlatFromFirebase(flatPassword);
    }

    // Presenter -> Model
    @Override
    public void addUserToFlat(String email, String flatID) {
        mJoinFlatModel.addUserToFlatInFirebase(email, flatID);
    }

    // When Model is successful
    @Override
    public void onSuccess(ArrayList<Flat> flats) {
        Log.d("given", flatID);
        for(int i = 0; i < flats.size() ; i++) {
            Flat currentFlat = flats.get(i);
            String currentID = currentFlat.getId();
            // filter through if-statement
            if(currentID.equals(flatID)){
                String foundID = currentFlat.getId();
                String foundOwner = currentFlat.getMembers().get(0);
                int foundSize = currentFlat.getSize();
                // Presenter -> View
                mJoinFlatView.onFlatFound(foundID, foundOwner, foundSize);
            }
        }
    }

    // interface method
    @Override
    public void onFailure(String message) {
    }
}