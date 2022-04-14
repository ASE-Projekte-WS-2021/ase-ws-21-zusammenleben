package Presenter.UserProfile;

import android.app.Activity;
import android.graphics.Bitmap;

public interface UserProfileContract {

    interface View{
        void onProfileSuccess(String message);
        void onProfileFailure(String message);
    }

    interface Presenter{
        void changePicture(Activity activity, Bitmap bitmap);
        //void setURL(StorageReference reference);
        //void setUserURL(URI uri);
    }

    interface onProfileListener{
        void onSuccess(String message);
        void onFailure(String message);
    }
}
