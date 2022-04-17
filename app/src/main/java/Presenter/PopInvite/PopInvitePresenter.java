package Presenter.PopInvite;

import android.util.Log;

import Model.PopInviteModel;

public class PopInvitePresenter implements PopInviteContract.Presenter, PopInviteContract.Listener {

    private PopInviteModel popInviteModel;
    private PopInviteContract.View mPopInviteView;
    String flatID;

    public PopInvitePresenter(PopInviteContract.View popInviteView){
        this.mPopInviteView = popInviteView;
        popInviteModel = new PopInviteModel(this);
    }

    @Override
    public void getFlatID(String email) {
        popInviteModel.getFlatIDFromFirebase(email);
    }

    @Override
    public void onFlatIDRetrieved(String id) {
        flatID = id;
        mPopInviteView.onFlatIDRetrieved(flatID);
    }
}
