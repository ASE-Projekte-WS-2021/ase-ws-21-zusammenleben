package Presenter.Overview;

import java.util.ArrayList;

import Entities.Payment;
import Model.OverviewModel;

public class OverviewPresenter implements OverviewContract.Presenter, OverviewContract.onPaymentListener{

    private OverviewContract.View mOverviewView;
    private OverviewModel mOverviewModel;

    public OverviewPresenter(OverviewContract.View overviewView){
        this.mOverviewView = overviewView;
        mOverviewModel = new OverviewModel(this);
    }

    @Override
    public void retrievePayment(String email) {

    }

    @Override
    public void onSuccess(ArrayList<Payment> payments) {

    }
}
