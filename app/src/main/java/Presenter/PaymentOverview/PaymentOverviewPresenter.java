package Presenter.PaymentOverview;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Entities.Flat;
import Entities.Payment;
import Model.PaymentOverviewModel;

public class PaymentOverviewPresenter implements PaymentOverviewContract.Presenter, PaymentOverviewContract.onPaymentSuccessListener {

    // MVP components
    private PaymentOverviewContract.View mPaymentOverviewView;
    private PaymentOverviewModel mPaymentOverviewModel;

    // Utils
    String currentUserEmail;
    Flat currentUserFlat;

    public PaymentOverviewPresenter(PaymentOverviewContract.View paymentOverviewView){
        this.mPaymentOverviewView = paymentOverviewView;
        mPaymentOverviewModel = new PaymentOverviewModel(this);
    }

    // Firebase -> Model
    @Override
    public void retrieveFlat(String email) {
        currentUserEmail = email;
        mPaymentOverviewModel.retrieveFlatFromFirebase(currentUserEmail);
    }

    // Firebase -> Model
    // Creating a new object and passing it to the Model
    @Override
    public void savePayment(double cost, String purpose, ArrayList<String> receivers) {
        ArrayList<String> myList = splitArrayList(receivers);
        Payment payment = new Payment(cost,purpose, currentUserEmail, myList, currentUserFlat.getId(), "");
        Log.d("123", payment.getFlatID());
        mPaymentOverviewModel.addPaymentToFirebase(payment);
    }

    // This method extracts the relevant data and separates it by the comma
    // Maybe not so elegant but this works since you are not allowed to enter "," inside your e-mail
    private ArrayList<String> splitArrayList(ArrayList<String> arrayList){
        String str = arrayList.get(0);
        List<String> myList = new ArrayList<String>(Arrays.asList(str.split(",")));
        return (ArrayList<String>) myList;
    }

    // get size of flat
    @Override
    public int retrieveFlatSize(Flat flat) {
        return flat.getMembers().size();
    }

    // if a checkbox is selected -> true, if not -> false. Thus we extract a boolean array
    @Override
    public boolean[] retrieveSelectedMembers(Flat flat) {
        boolean[] arr = new boolean[flat.getMembers().size()];
        return arr;
    }

    // get the names connected to the checkbox
    @Override
    public String[] retrieveMemberNames(Flat flat) {
        String[] arr = new String[flat.getMembers().size()];
        for(int i = 0 ; i < arr.length ; i++){
            arr[i] = flat.getMembers().get(i);
        }
        return arr;
    }

    // Presenter -> Model with the extracted data
    @Override
    public void updatePayment(double cost, String purpose, ArrayList<String> receivers, String flatID, String paymentID) {
        ArrayList<String> myList = splitArrayList(receivers);
        Payment payment = new Payment(cost,purpose, currentUserEmail, myList, flatID, paymentID);
        mPaymentOverviewModel.updatePaymentInFirebase(payment);
    }

    // Presenter -> View
    @Override
    public void onFlatFoundSuccess(Flat flat) {
        currentUserFlat = flat;
        mPaymentOverviewView.onFlatFound(currentUserFlat);
    }

    // Presenter -> View
    @Override
    public void onSuccess() {
        mPaymentOverviewView.startIntent();
    }

    // interface method
    @Override
    public void deletePayment(String paymentID) {
    }
}
