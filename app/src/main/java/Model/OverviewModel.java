package Model;

import java.util.ArrayList;

import Entities.Payment;
import Presenter.Overview.OverviewContract;

public class OverviewModel implements OverviewContract.Model, OverviewContract.onPaymentListener {

    private OverviewContract.onPaymentListener mOnPaymentListener;

    public OverviewModel(OverviewContract.onPaymentListener onPaymentListener){
        this.mOnPaymentListener = onPaymentListener;
    }

    @Override
    public ArrayList<Payment> retrievePaymentFromFirebase(String email) {
        return null;
    }

    @Override
    public void onSuccess(ArrayList<Payment> payments) {

    }
}
