package Presenter.JoinFlat;

import java.util.ArrayList;

import Entities.Flat;

public interface JoinFlatContract {

    interface View{
        void onFlatFound(String founder, String address, int size);
        void onFlatNotFound(String message);
    }

    interface Presenter{
        void retrieveFlat(String flatPassword);
        void addUserToFlat(String email, String flatID);
    }

    interface Model{
        ArrayList<Flat> retrieveFlatFromFirebase(String flatPassword);
        void addUserToFlatInFirebase(String email, String flatID);
    }

    interface onJoinFlatListener{
        void onSuccess(ArrayList<Flat> flats);
        void onFailure(String message);
    }
}
