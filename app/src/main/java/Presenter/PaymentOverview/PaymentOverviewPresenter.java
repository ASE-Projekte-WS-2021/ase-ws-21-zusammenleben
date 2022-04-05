package Presenter.PaymentOverview;

import java.util.ArrayList;

import Entities.Payment;
import Model.PaymentOverviewModel;

public class PaymentOverviewPresenter implements PaymentOverviewContract.Presenter, PaymentOverviewContract.onPaymentSuccessListener {

    private PaymentOverviewContract.View mPaymentOverviewView;
    private PaymentOverviewModel mPaymentOverviewModel;

    public PaymentOverviewPresenter(PaymentOverviewContract.View paymentOverviewView){
        this.mPaymentOverviewView = paymentOverviewView;
        mPaymentOverviewModel = new PaymentOverviewModel(this);
    }

    @Override
    public void retrieveFlat(String email) {

    }

    @Override
    public void retrievePaymentFromFlat(String flatID) {

    }

    @Override
    public void onSuccess(ArrayList<Payment> payments) {

    }
}
