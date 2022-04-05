package Presenter.PaymentOverview;

import java.util.ArrayList;

import Entities.Payment;

public interface PaymentOverviewContract {

    interface View{
        void onPaymentFound(Payment payment);
    }

    interface Presenter{
        void retrieveFlat(String email);
        void retrievePaymentFromFlat(String flatID);
    }

    interface Model{
        String retrieveFlatFromFirebase(String email);
        void addPaymentToFirebase(Payment p);
        }

    interface onPaymentSuccessListener{
        void onSuccess(ArrayList<Payment> payments);
    }
}
