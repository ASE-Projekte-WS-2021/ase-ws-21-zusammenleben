package Presenter.Overview;

import java.util.ArrayList;

import Entities.Payment;

public interface OverviewContract {

    interface View{
        void onPaymentFound(ArrayList<ArrayList <String>> paymentsList, double debt);
        void onPaymentNotFound();
    }

    interface Presenter{
        void retrievePayment(String email);
        void deletePayment(String id);
    }

    interface Model{
        ArrayList<Payment> retrievePaymentFromFirebase(String email);
        void deletePaymentFromFirebase(String id);
    }

    interface onPaymentListener{
        void onSuccess(ArrayList<Payment> payments);
    }
}
