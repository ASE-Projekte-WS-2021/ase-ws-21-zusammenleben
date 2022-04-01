package Presenter.AddWG;

import android.app.Activity;

import java.util.List;

import Entities.WG;

public interface AddWGContract {

    interface View{
        void onAddWGSuccess();
        void onAddWGFailed();
    }

    interface Presenter{
        void addWG(Activity activity, String address, String id, List<String> members, int size);
    }

    interface Model{
        void addWGtoFirebase(WG wg);
    }

    interface onAddWGListener{
        void onSuccess(String message);
        void onFailure(String message);
    }
}
