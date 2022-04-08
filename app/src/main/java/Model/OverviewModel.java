package Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Entities.Payment;
import Presenter.Overview.OverviewContract;

public class OverviewModel implements OverviewContract.Model, OverviewContract.onPaymentListener {

    private OverviewContract.onPaymentListener mOnPaymentListener;
    ArrayList<Payment> payments = new ArrayList<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance(FIREBASEPATH);
    private DatabaseReference refPayment = database.getReference(PAYMENTPATH);
    private static final String FIREBASEPATH = "https://wgfinance-b594f-default-rtdb.europe-west1.firebasedatabase.app/";
    private static final String PAYMENTPATH = "Payment";


    public OverviewModel(OverviewContract.onPaymentListener onPaymentListener){
        this.mOnPaymentListener = onPaymentListener;
    }

    @Override
    public ArrayList<Payment> retrievePaymentFromFirebase(String email) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    if(snap.getValue(Payment.class).getCreator().equals(email) || snap.getValue(Payment.class).getReceiver().contains(email)){
                        ArrayList<String> receivers = snap.getValue(Payment.class).getReceiver();
                        String creator = snap.getValue(Payment.class).getCreator();
                        double cost = snap.getValue(Payment.class).getCost();
                        String purpose = snap.getValue(Payment.class).getPurpose();
                        String flatID = snap.getValue(Payment.class).getFlatID();
                        String paymentID = snap.getValue(Payment.class).getPaymentID();
                        Payment p = new Payment(cost, purpose, creator, receivers, flatID, paymentID);
                        payments.add(p);
                        Log.d("model1", String.valueOf(payments.size()));
                    }
                }
                mOnPaymentListener.onSuccess(payments);
                payments.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        refPayment.addListenerForSingleValueEvent(valueEventListener);
        return payments;
    }

    @Override
    public void deletePaymentFromFirebase(String id) {
        refPayment.child(id).removeValue();
    }

    @Override
    public void onSuccess(ArrayList<Payment> payments) {
    }
}
