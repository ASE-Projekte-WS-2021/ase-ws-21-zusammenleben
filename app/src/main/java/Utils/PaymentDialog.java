package Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import View.After.ActivityOverview;
import View.After.ActivityPaymentOverview;

public class PaymentDialog extends AppCompatDialogFragment {

    FirebaseDatabase database;
    DatabaseReference databaseReferencePayment;

    String arrivedPurpose, arrivedReceiver, arrivedCost;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Payment")
                .setMessage("Edit or delete this payment")
                .setPositiveButton("edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bundle receiverBundle = getArguments();
                        String transmittedPurpose = receiverBundle.getString("PAYMENTPURPOSE", "");
                        String transmittedCost = receiverBundle.getString("PAYMENTCOST", "");
                        Intent intent = new Intent(getContext(), ActivityPaymentOverview.class);
                        Bundle sendBundle = new Bundle();
                        sendBundle.putBoolean("STATE", true);
                        sendBundle.putString("PAYMENTPURPOSE", transmittedPurpose);
                        sendBundle.putString("PAYMENTCOST", transmittedCost);
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
    }

}
