package Presenter.AddWG;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    public void addWG(Activity activity, String id, int size, List<String> members, String address) {
        WG createdWG = new WG(id, size, members, address);
        mAddWGModel.addWGtoFirebase(createdWG);
    }

    @Override
    public void onSuccess(String message) {
    }

    @Override
    public void onFailure(String message) {
    }
}
