package Presenter.AddFlat;

import android.app.Activity;

import java.util.List;

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
        Flat createdFlat = new Flat(address, id, members, size);
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
