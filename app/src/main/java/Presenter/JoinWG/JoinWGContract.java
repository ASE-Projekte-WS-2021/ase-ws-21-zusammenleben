package Presenter.JoinWG;

import java.util.ArrayList;

import Entities.WG;

public interface JoinWGContract {

    interface View{
        void onWGFound(String founder, String address, int size);
        void onWGNotFound(String message);
    }

    interface Presenter{
        void retrieveFlat(String flatPassword);
        WG sendFlat();
        void addUserToFlat(String email, String flatID);
    }

    interface Model{
        ArrayList<WG> retrieveFlatFromFirebase(String flatPassword);
        void addUserToFlatInFirebase(String email, String flatID);
    }

    interface onJoinWGListener{
        void onSuccess(ArrayList<WG> wgs);
        void onFailure(String message);
    }
}
