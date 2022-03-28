package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PaymentDialog extends AppCompatDialogFragment {

    FirebaseDatabase database;
    DatabaseReference databaseReferencePayment;

    String arrivedPurpose, arrivedReceiver, arrivedCost;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        initFirebase();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Payment")
                .setMessage("Edit or delete this payment")
                .setPositiveButton("edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("button", "edit");
                        Bundle receiverBundle = getArguments();
                        String paymentData = receiverBundle.getString("PAYMENT", "");
                        Intent intent = new Intent(getContext(), ActivityEditPayment.class);
                        Bundle sendBundle = new Bundle();
                        sendBundle.putString("PAYMENT", paymentData);
                        intent.putExtras(sendBundle);
                        startActivity(intent);
                    }
                });

        builder.setNegativeButton("delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("button", "delete");
                Bundle receiverBundle = getArguments();
                String paymentData = receiverBundle.getString("PAYMENT", "");
                System.out.println(paymentData);
                unpackArrivedData(paymentData);
                Intent intent = new Intent(getContext(), ActivityOverview.class);
                startActivity(intent);
            }
        });
        return builder.create();
    }

    private void unpackArrivedData(String str){
        String[] arrivedData = str.split("/");
        arrivedPurpose = arrivedData[0];
        arrivedReceiver = arrivedData[1];
        arrivedCost = arrivedData[2];
        deletePaymentFromFirebase();
    }

    private void deletePaymentFromFirebase() {
        databaseReferencePayment.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ArrayList<String> currentPayment = dataSnapshot.getValue(PaymentMemo.class).getData();
                        if(currentPayment.contains(arrivedPurpose) && currentPayment.contains(arrivedReceiver) && currentPayment.contains(arrivedCost)){
                            dataSnapshot.getRef().removeValue();
                            return;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("catch", "Database still empty.....");
            }
        });
    }

    private void initFirebase(){
        database = FirebaseDatabase.getInstance("https://my-application-f648a-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReferencePayment = database.getReference("Payments");
    }

}
