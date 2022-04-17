package Presenter.UserProfile;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;

import Model.UserProfileModel;

public class UserProfilePresenter implements UserProfileContract.Presenter, UserProfileContract.onProfileListener {

    private UserProfileContract.View mProfileView;
    private UserProfileModel mProfileModel;

    public UserProfilePresenter (UserProfileContract.View mProfileView){
        this.mProfileView = mProfileView;
        mProfileModel = new UserProfileModel(this);
    }

    @Override
    public void changePicture(Activity activity, Bitmap bitmap) {
        mProfileModel.uploadimage(activity, bitmap);
    }

    @Override
    public void onSuccess(String message) {
        mProfileView.onProfileSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        mProfileView.onProfileFailure(message);
    }
}
