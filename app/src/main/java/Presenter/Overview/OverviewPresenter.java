package Presenter.Overview;
import java.util.ArrayList;
import Entities.Payment;
import Model.OverviewModel;

public class OverviewPresenter implements OverviewContract.Presenter, OverviewContract.onPaymentListener{

    // MVP components
    private OverviewContract.View mOverviewView;
    private OverviewModel mOverviewModel;

    // Utils
    ArrayList<ArrayList <String>> paymentsList = new ArrayList<>();
    ArrayList<Payment> debtList = new ArrayList<>();
    String currentUserEmail;
    double debt;

    public OverviewPresenter(OverviewContract.View overviewView){
        this.mOverviewView = overviewView;
        mOverviewModel = new OverviewModel(this);
    }

    // Presenter -> Model
    @Override
    public void retrievePayment(String email) {
        currentUserEmail = email;
        mOverviewModel.retrievePaymentFromFirebase(email);
    }

    // Presenter -> Model
    @Override
    public void deletePayment(String id) {
        mOverviewModel.deletePaymentFromFirebase(id);
    }

    // After Model is finished - prepare data for the View
    // This method does two things - prepare data for View's recyclerview and View's debt text
    @Override
    public void onSuccess(ArrayList<Payment> payments) {
        debtList.clear();
        for(int i = 0 ; i < payments.size() ; i++){
            // unpack the attached data
            String purpose = payments.get(i).getPurpose();
            String receivers = payments.get(i).getReceiver().toString();
            String cost = String.valueOf(payments.get(i).getCost());
            String flatID = payments.get(i).getFlatID();
            String paymentID = payments.get(i).getPaymentID();
            // create appropriate ArrayList for Recyclerview
            ArrayList<String> arrList = new ArrayList<>();
            arrList.add(purpose);
            arrList.add(receivers);
            arrList.add(cost);
            arrList.add(flatID);
            arrList.add(paymentID);
            paymentsList.add(arrList);
            // chose another, less complex payment object, attach it to ArrayList
            Payment debtPayment = new Payment(Double.valueOf(cost), receivers);
            debtList.add(debtPayment);
        }
        calculateDebts();
        debtList.clear();
        // Presenter -> View
        mOverviewView.onPaymentFound(paymentsList, debt);
    }

    // calculating the sum of each payment, thus the debt
    private void calculateDebts(){
        debt = 0.0;
        for(int i = 0; i < debtList.size(); i++){
            String receivers = debtList.get(i).getReceiverString();
            double cost = debtList.get(i).getCost();
            // filter the payments by users with email
            if(receivers.contains(currentUserEmail)){
                int counter = 1;
                for(int j = 0 ; j < receivers.length() ; j++){
                    if(receivers.charAt(j) == ','){
                        counter++;
                    }
                }
                // divide the sum by the number of receivers
                double currentDebt = cost / counter;
                debt = debt + currentDebt;
            }
        }
    }
}
