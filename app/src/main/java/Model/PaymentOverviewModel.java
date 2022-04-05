package Model;

import java.util.ArrayList;

import Entities.Payment;
import Presenter.PaymentOverview.PaymentOverviewContract;

public class PaymentOverviewModel implements PaymentOverviewContract.Model, PaymentOverviewContract.onPaymentSuccessListener {

    private PaymentOverviewContract.onPaymentSuccessListener onPaymentSuccessListener;

    public PaymentOverviewModel(PaymentOverviewContract.onPaymentSuccessListener onPaymentSuccessListener){
        this.onPaymentSuccessListener = onPaymentSuccessListener;
    }

    @Override
    public String retrieveFlatFromFirebase(String email) {
        return null;
    }

    @Override
    public void addPaymentToFirebase(Payment p) {

    }

    @Override
    public void onSuccess(ArrayList<Payment> payments) {

    }
}
