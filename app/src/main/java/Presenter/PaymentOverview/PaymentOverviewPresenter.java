package Presenter.PaymentOverview;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        ArrayList<String> myList = splitArrayList(receivers);
        Payment payment = new Payment(cost,purpose, currentUserEmail, myList, currentUserFlat.getId(), "");
        Log.d("123", payment.getFlatID());
        mPaymentOverviewModel.addPaymentToFirebase(payment);
    }

    private ArrayList<String> splitArrayList(ArrayList<String> arrayList){
        String str = arrayList.get(0);
        List<String> myList = new ArrayList<String>(Arrays.asList(str.split(",")));
        return (ArrayList<String>) myList;
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
    public void deletePayment(String paymentID) {
        Log.d("angekommen", paymentID);
    }

    @Override
    public void updatePayment(double cost, String purpose, ArrayList<String> receivers, String flatID, String paymentID) {
        ArrayList<String> myList = splitArrayList(receivers);
        Payment payment = new Payment(cost,purpose, currentUserEmail, myList, flatID, paymentID);
        mPaymentOverviewModel.updatePaymentInFirebase(payment);
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
    public void onSuccess() {
        mPaymentOverviewView.startIntent();
    }
}
