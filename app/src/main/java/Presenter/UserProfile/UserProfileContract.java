package Presenter.UserProfile;

import android.app.Activity;
import android.graphics.Bitmap;

import Entities.Flat;

public interface UserProfileContract {

    interface View{
        void onProfileSuccess(String message);
        void onProfileFailure(String message);
        void onUserDeletedSuccess(String message);
    }

    interface Presenter{
        void changePicture(Activity activity, Bitmap bitmap);
        void deleteUser(String mail);
    }

    interface Model{
        Flat retrieveFlatFromFirebase(String mail);
        void deleteUserInFirebase(Flat flat);
    }

    interface onProfileListener{
        void onSuccess(String message);
        void onFailure(String message);
    }

    interface onUserDeletedListener{
        void onFlatFound(Flat flat);
        void onUserDeleted(String message);
    }
}
