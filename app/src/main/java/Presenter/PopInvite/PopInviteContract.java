package Presenter.PopInvite;

public interface PopInviteContract {

    interface View{
        void onFlatIDRetrieved(String email);
    }

    interface Presenter{
        void getFlatID(String email);
    }

    interface Model{
        void getFlatIDFromFirebase(String email);
    }

    interface Listener{
        void onFlatIDRetrieved(String email);
    }
}
