package Presenter.AddFlat;

import android.app.Activity;
import android.util.Log;

import java.util.List;
import java.util.UUID;

import Entities.Flat;
import Model.AddFlatModel;

public class AddFlatPresenter implements AddFlatContract.Presenter, AddFlatContract.onAddFlatListener{

    private AddFlatContract.View mAddFlatView;
    private AddFlatModel mAddFlatModel;

    public AddFlatPresenter(AddFlatContract.View mAddFlatView){
        this.mAddFlatView = mAddFlatView;
        mAddFlatModel = new AddFlatModel(this);
    }

    @Override
    public void addFlat(Activity activity, String address, String id, List<String> members, int size) {
        String randomID = UUID.randomUUID().toString().substring(0,5);
        String flatID = id+randomID;
        Flat createdFlat = new Flat(address, flatID, members, size);
        mAddFlatModel.addFlattoFirebase(createdFlat);
    }

    @Override
    public void onSuccess(String message) {
        mAddFlatView.onAddFlatSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        mAddFlatView.onAddFlatFailed(message);
    }
}
