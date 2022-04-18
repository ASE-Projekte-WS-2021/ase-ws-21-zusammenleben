package Presenter.PopInvite;
import android.util.Log;

import Model.PopInviteModel;

public class PopInvitePresenter implements PopInviteContract.Presenter, PopInviteContract.Listener {

    // MVP components
    private PopInviteModel popInviteModel;
    private PopInviteContract.View mPopInviteView;

    // Util
    String flatID;

    public PopInvitePresenter(PopInviteContract.View popInviteView){
        this.mPopInviteView = popInviteView;
        popInviteModel = new PopInviteModel(this);
    }

    // Presenter -> Model. Get Data from the model
    @Override
    public void getFlatID(String email) {
        popInviteModel.getFlatIDFromFirebase(email);
    }

    // Presenter -> View
    @Override
    public void onFlatIDRetrieved(String id) {
        flatID = id;
        Log.d("test4", id);
        mPopInviteView.onFlatIDRetrieved(flatID);
    }
}
