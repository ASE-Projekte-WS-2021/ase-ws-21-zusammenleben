package Presenter.PaymentOverview;

import java.util.ArrayList;

import Entities.Flat;
import Entities.Payment;

public interface PaymentOverviewContract {

    interface View{
        void onFlatFound(Flat flat);
        void onPaymentFound(Payment payment);
    }

    interface Presenter{
        void retrieveFlat(String email);
        void savePayment(Payment payment);

        int retrieveFlatSize(Flat flat);
        boolean[] retrieveSelectedMembers(Flat flat);
        // TODO : userEmail -> userName
        String[] retrieveMemberNames(Flat flat);
    }

    // 1. int - Größe der WG = members.size()
    // 2. Boolean Array mit der Größe 1. - false initialisieren
    // 3. Namen der WG Mitglieder

    interface Model{
        Flat retrieveFlatFromFirebase(String email);
        void addPaymentToFirebase(Payment payment);
        }

    interface onPaymentSuccessListener{
        void onFlatFoundSuccess(Flat flat);
        void onSuccess(ArrayList<Payment> payments);
    }
}
