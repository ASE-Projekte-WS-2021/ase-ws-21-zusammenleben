package Presenter.Overview;

import java.util.ArrayList;

import Entities.Payment;

public interface OverviewContract {

    interface View{
        void onPaymentFound(Payment payment);
        void onPaymentNotFound();
    }

    interface Presenter{
        void retrievePayment(String email);
    }

    interface Model{
        ArrayList<Payment> retrievePaymentFromFirebase(String email);
    }

    interface onPaymentListener{
        void onSuccess(ArrayList<Payment> payments);
    }
}
