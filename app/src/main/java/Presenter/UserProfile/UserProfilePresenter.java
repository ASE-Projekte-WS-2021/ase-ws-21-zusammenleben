package Presenter.UserProfile;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

import Entities.Flat;
import Entities.User;
import Model.UserProfileModel;

public class UserProfilePresenter implements UserProfileContract.Presenter, UserProfileContract.onProfileListener, UserProfileContract.onUserDeletedListener {

    private UserProfileContract.View mProfileView;
    private UserProfileModel mProfileModel;
    Flat retrievedFlat;
    String userMail;

    public UserProfilePresenter (UserProfileContract.View mProfileView){
        this.mProfileView = mProfileView;
        mProfileModel = new UserProfileModel(this);
    }

    @Override
    public void changePicture(Activity activity, Bitmap bitmap) {
        mProfileModel.uploadimage(activity, bitmap);
    }

    @Override
    public void deleteUser(String mail) {
        userMail = mail;
        mProfileModel.retrieveFlatFromFirebase(mail);
    }

    @Override
    public void onSuccess(String message) {
        mProfileView.onProfileSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        mProfileView.onProfileFailure(message);
    }

    @Override
    public void onFlatFound(Flat flat) {
        ArrayList<String> members = (ArrayList<String>) flat.getMembers();
        for(int i=0; i<members.size(); i++){
            if(members.get(i).equals(userMail)){
                members.remove(i);
            }
        }
        String id = flat.getId();
        String address = flat.getAddress();
        int size = flat.getSize();
        Flat updatedFlat = new Flat(address, id, members, size);
        mProfileModel.deleteUserInFirebase(updatedFlat);
    }

    @Override
    public void onUserDeleted(String message) {
        mProfileView.onUserDeletedSuccess(message);
    }
}
