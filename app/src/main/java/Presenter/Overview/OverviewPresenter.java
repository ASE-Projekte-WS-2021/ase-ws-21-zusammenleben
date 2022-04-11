package Presenter.Overview;

import android.util.Log;

import java.util.ArrayList;

import Entities.Payment;
import Model.OverviewModel;

public class OverviewPresenter implements OverviewContract.Presenter, OverviewContract.onPaymentListener{

    private OverviewContract.View mOverviewView;
    private OverviewModel mOverviewModel;
    ArrayList<ArrayList <String>> paymentsList = new ArrayList<>();
    OverviewContract.onPaymentListener mOnPaymentListener;

    public OverviewPresenter(OverviewContract.View overviewView){
        this.mOverviewView = overviewView;
        mOverviewModel = new OverviewModel(this);
    }

    @Override
    public void retrievePayment(String email) {
        Log.d("123", "triggered in Presenter!");
        mOverviewModel.retrievePaymentFromFirebase(email);
    }

    @Override
    public void deletePayment(String id) {
        mOverviewModel.deletePaymentFromFirebase(id);
    }

    @Override
    public void onSuccess(ArrayList<Payment> payments) {
        Log.d("123 wieder in presenter", String.valueOf(payments.size()));
        for(int i = 0 ; i < payments.size() ; i++){
            String purpose = payments.get(i).getPurpose();
            String receivers = payments.get(i).getReceiver().toString();
            String cost = String.valueOf(payments.get(i).getCost());
            String flatID = payments.get(i).getFlatID();
            String paymentID = payments.get(i).getPaymentID();
            ArrayList<String> arrList = new ArrayList<>();
            arrList.add(purpose);
            arrList.add(receivers);
            arrList.add(cost);
            arrList.add(flatID);
            arrList.add(paymentID);
            paymentsList.add(arrList);
        }
        Log.d("123 view size ", String.valueOf(paymentsList.size()));
        mOverviewView.onPaymentFound(paymentsList);
    }
}
