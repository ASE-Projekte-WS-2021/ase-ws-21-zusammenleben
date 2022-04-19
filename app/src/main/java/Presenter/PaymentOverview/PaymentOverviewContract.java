package Presenter.PaymentOverview;
import java.util.ArrayList;
import Entities.Flat;
import Entities.Payment;

public interface PaymentOverviewContract {

    interface View{
        void onFlatFound(Flat flat);
        void onPaymentFound(Payment payment);
        void startIntent();
    }

    interface Presenter{
        void retrieveFlat(String email);
        void savePayment(double cost, String purpose, ArrayList<String> receivers);
        int retrieveFlatSize(Flat flat);
        boolean[] retrieveSelectedMembers(Flat flat);
        // TODO : userEmail -> userName
        String[] retrieveMemberNames(Flat flat);

        void deletePayment(String paymentID);

        void updatePayment(double cost, String purpose, ArrayList<String> receivers, String flatID, String paymentID);
    }

    interface Model{
        Flat retrieveFlatFromFirebase(String email);
        void addPaymentToFirebase(Payment payment);
        void updatePaymentInFirebase(Payment payment);
        }

    interface onPaymentSuccessListener{
        void onFlatFoundSuccess(Flat flat);
        void onSuccess();
    }
}
