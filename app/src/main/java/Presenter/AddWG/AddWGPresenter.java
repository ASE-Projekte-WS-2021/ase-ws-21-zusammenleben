package Presenter.AddWG;

import android.app.Activity;

import java.util.List;

import Entities.WG;
import Model.AddWGModel;

public class AddWGPresenter implements AddWGContract.Presenter, AddWGContract.onAddWGListener{

    private AddWGContract.View mAddWGView;
    private AddWGModel mAddWGModel;

    public AddWGPresenter(AddWGContract.View addWGView){
        this.mAddWGView = addWGView;
        mAddWGModel = new AddWGModel(this);
    }

    @Override
    public void addWG(Activity activity, String address, String id, List<String> members, int size) {
        WG createdWG = new WG(address, id, members, size);
        mAddWGModel.addWGtoFirebase(createdWG);
    }

    @Override
    public void onSuccess(String message) {
    }

    @Override
    public void onFailure(String message) {
    }
}
