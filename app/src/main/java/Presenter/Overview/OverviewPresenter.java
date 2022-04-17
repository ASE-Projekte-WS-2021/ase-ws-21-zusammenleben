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
    String currentUserEmail;
    double debt;

    ArrayList<Payment> debtList = new ArrayList<>();

    public OverviewPresenter(OverviewContract.View overviewView){
        this.mOverviewView = overviewView;
        mOverviewModel = new OverviewModel(this);
    }

    @Override
    public void retrievePayment(String email) {
        currentUserEmail = email;
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
        debtList.clear();
        for(int i = 0 ; i < payments.size() ; i++){
            String purpose = payments.get(i).getPurpose();
            String receivers = payments.get(i).getReceiver().toString();
            String cost = String.valueOf(payments.get(i).getCost());
            String flatID = payments.get(i).getFlatID();
            String paymentID = payments.get(i).getPaymentID();
            ArrayList<String> arrList = new ArrayList<>();
            Payment debtPayment = new Payment(Double.valueOf(cost), receivers);
            debtList.add(debtPayment);
            arrList.add(purpose);
            arrList.add(receivers);
            arrList.add(cost);
            arrList.add(flatID);
            arrList.add(paymentID);
            paymentsList.add(arrList);
        }
        calculateDebts();
        debtList.clear();
        mOverviewView.onPaymentFound(paymentsList, debt);
    }

    private void calculateDebts(){
        Log.d("check1", String.valueOf(debtList.size()));
        for(int i = 0; i < debtList.size(); i++){
            String receivers = debtList.get(i).getReceiverString();
            double cost = debtList.get(i).getCost();
            if(receivers.contains(currentUserEmail)){
                int counter = 1;
                for(int j = 0 ; j < receivers.length() ; j++){
                    if(receivers.charAt(j) == ','){
                        counter++;
                    }
                }

                double currentDebt = cost / counter;
                debt = debt + currentDebt;
            }
        }
        Log.d("test", String.valueOf(debt));
    }
}
