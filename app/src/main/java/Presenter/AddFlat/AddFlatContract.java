package Presenter.AddFlat;

import android.app.Activity;

import java.util.List;

import Entities.Flat;

public interface AddFlatContract {

    interface View{
        void onAddFlatSuccess();
        void onAddFlatFailed();
    }

    interface Presenter{
        void addFlat(Activity activity, String address, String id, List<String> members, int size);
    }

    interface Model{
        void addFlattoFirebase(Flat flat);
    }

    interface onAddFlatListener{
        void onSuccess(String message);
        void onFailure(String message);
    }
}
