package Presenter.PaymentOverview;

import android.util.Log;

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
    public void retrievePaymentFromFlat(String flatID) {

    }

    @Override
    public void onFlatFoundSuccess(Flat flat) {
        currentUserFlat = flat;
        String id = currentUserFlat.getId();
        Log.d("retrieved flat name = ", id);
        mPaymentOverviewView.onFlatFound(currentUserFlat);
    }

    @Override
    public void onSuccess(ArrayList<Payment> payments) {

    }
}
