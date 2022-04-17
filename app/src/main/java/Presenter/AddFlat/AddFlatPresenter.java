package Presenter.AddFlat;

import android.app.Activity;
import android.util.Log;

import java.util.List;
import java.util.UUID;

import Entities.Flat;
import Model.AddFlatModel;

public class AddFlatPresenter implements AddFlatContract.Presenter, AddFlatContract.onAddFlatListener{

    // MVP components
    private AddFlatContract.View mAddFlatView;
    private AddFlatModel mAddFlatModel;

    public AddFlatPresenter(AddFlatContract.View mAddFlatView){
        this.mAddFlatView = mAddFlatView;
        mAddFlatModel = new AddFlatModel(this);
    }

    // Presenter -> Model
    // We add a 5-character-random-code to the flatID to make sure that the flatID is always unique
    // This is important for later usage in DeepLink and JoinFlat
    @Override
    public void addFlat(Activity activity, String name, String id, List<String> members, int size) {
        String randomID = UUID.randomUUID().toString().substring(0,5);
        String flatID = id+randomID;
        Flat createdFlat = new Flat(name, flatID, members, size);
        mAddFlatModel.addFlattoFirebase(createdFlat);
    }

    // Presenter -> View
    @Override
    public void onSuccess(String message) {
        mAddFlatView.onAddFlatSuccess(message);
    }

    // Presenter -> View
    @Override
    public void onFailure(String message) {
        mAddFlatView.onAddFlatFailed(message);
    }
}
