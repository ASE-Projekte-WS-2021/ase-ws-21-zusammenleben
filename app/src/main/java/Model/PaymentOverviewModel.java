package Model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Entities.Flat;
import Entities.Payment;
import Presenter.PaymentOverview.PaymentOverviewContract;
import androidx.annotation.NonNull;

public class PaymentOverviewModel implements PaymentOverviewContract.Model, PaymentOverviewContract.onPaymentSuccessListener {

    private PaymentOverviewContract.onPaymentSuccessListener onPaymentSuccessListener;
    private static final String FIREBASEPATH = "https://wgfinance-b594f-default-rtdb.europe-west1.firebasedatabase.app/";
    private static final String FLATPATH = "WG";
    private static final String PAYMENT = "Payment";
    private FirebaseDatabase database = FirebaseDatabase.getInstance(FIREBASEPATH);
    private DatabaseReference refFlat = database.getReference(FLATPATH);
    private DatabaseReference refPayment = database.getReference(PAYMENT);
    Flat retrievedFlat;

    ArrayList<Flat> payments = new ArrayList<>();


    public PaymentOverviewModel(PaymentOverviewContract.onPaymentSuccessListener onPaymentSuccessListener){
        this.onPaymentSuccessListener = onPaymentSuccessListener;
    }

    @Override
    public Flat retrieveFlatFromFirebase(String email) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    List<String> members = snap.getValue(Flat.class).getMembers();
                    if(members.contains(email)){
                        String address = snap.getValue(Flat.class).getAddress();
                        String id = snap.getValue(Flat.class).getId();
                        int size = members.size();
                        retrievedFlat = new Flat(address, id, members, size);
                    }
                }
                onPaymentSuccessListener.onFlatFoundSuccess(retrievedFlat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        refFlat.addListenerForSingleValueEvent(valueEventListener);
        return retrievedFlat;
    }

    @Override
    public void addPaymentToFirebase(Payment p) {
        System.out.println("it Worked");
        refPayment.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //add Data to RTDB
                //Intent to ActivityOverview
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("error occured", error.toString());
            }
        });
    }

    @Override
    public void onFlatFoundSuccess(Flat flat) {
    }

    @Override
    public void onSuccess(ArrayList<Payment> payments) {

    }
}
