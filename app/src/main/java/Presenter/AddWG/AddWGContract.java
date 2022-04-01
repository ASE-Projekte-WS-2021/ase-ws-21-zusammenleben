package Presenter.AddWG;

import android.app.Activity;

import java.util.List;

import javax.security.auth.callback.Callback;

import Entities.WG;

public interface AddWGContract {

    interface View{
        void onAddWGSuccess();
        void onAddWGFailed();
    }

    interface Presenter{
        void addWG(Activity activity, String id, int size, List<String> members, String address);
    }

    interface Model{
        void addWGtoFirebase(WG wg);
    }

    interface onAddWGListener{
        void onSuccess(String message);
        void onFailure(String message);
    }
}
