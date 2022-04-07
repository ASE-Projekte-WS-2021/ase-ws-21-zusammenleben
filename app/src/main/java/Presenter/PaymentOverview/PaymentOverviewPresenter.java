package Presenter.PaymentOverview;

import java.util.ArrayList;

import Entities.Flat;
import Entities.Payment;
import Model.PaymentOverviewModel;

public class PaymentOverviewPresenter implements PaymentOverviewContract.Presenter, PaymentOverviewContract.onPaymentSuccessListener {

    private PaymentOverviewContract.View mPaymentOverviewView;
    private PaymentOverviewModel mPaymentOverviewModel;
    String currentUserEmail;
    Flat currentUserFlat;

    public PaymentOverviewPresenter(PaymentOverviewContract.View paymentOverviewView){
        this.mPaymentOverviewView = paymentOverviewView;
        mPaymentOverviewModel = new PaymentOverviewModel(this);
    }

    @Override
    public void retrieveFlat(String email) {
        currentUserEmail = email;
        mPaymentOverviewModel.retrieveFlatFromFirebase(currentUserEmail);
    }

    @Override
    public void savePayment(double cost, String purpose, ArrayList<String> receivers) {
        Payment payment = new Payment(cost,purpose, currentUserEmail, receivers, currentUserFlat.getId());
        mPaymentOverviewModel.addPaymentToFirebase(payment);

    }

    @Override
    public int retrieveFlatSize(Flat flat) {
        return flat.getMembers().size();
    }

    @Override
    public boolean[] retrieveSelectedMembers(Flat flat) {
        boolean[] arr = new boolean[flat.getMembers().size()];
        return arr;
    }

    @Override
    public String[] retrieveMemberNames(Flat flat) {
        String[] arr = new String[flat.getMembers().size()];
        for(int i = 0 ; i < arr.length ; i++){
            arr[i] = flat.getMembers().get(i);
        }
        return arr;
    }

    @Override
    public void onFlatFoundSuccess(Flat flat) {
        currentUserFlat = flat;
        unpackFlatData();
        mPaymentOverviewView.onFlatFound(currentUserFlat);
    }

    public void unpackFlatData(){

    }

    @Override
    public void onSuccess(ArrayList<Payment> payments) {

    }
}
