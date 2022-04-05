package Presenter.PaymentOverview;

import java.util.ArrayList;

import Entities.Flat;
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
        Flat retrieveFlatFromFirebase(String email);
        void addPaymentToFirebase(Payment p);
        }

    interface onPaymentSuccessListener{
        void onFlatFoundSuccess(Flat flat);
        void onSuccess(ArrayList<Payment> payments);
    }
}
