package Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import View.After.ActivityPaymentOverview;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.R;

public class PaymentDialog extends AppCompatDialogFragment implements DialogListener{

    // The dialog is the middle man between two activities - ShoppingList and PaymentOverview
    // Here we connect our two features, the payment and the shoppinglist

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // initialize
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setTitle("Zahlung Bearbeiten")
                .setMessage("Bearbeite oder Lösche eine Zahlung")
                .setPositiveButton("Bearbeiten", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bundle receiverBundle = getArguments();
                        String transmittedPurpose = receiverBundle.getString("PAYMENTPURPOSE", "");
                        String transmittedCost = receiverBundle.getString("PAYMENTCOST", "");
                        String transmittedFlatID = receiverBundle.getString("FLATID", "");
                        String transmittedPaymentID = receiverBundle.getString("PAYMENTID", "");
                        Intent intent = new Intent(getContext(), ActivityPaymentOverview.class);
                        Bundle sendBundle = new Bundle();
                        sendBundle.putBoolean("STATE", true);
                        sendBundle.putString("PAYMENTPURPOSE", transmittedPurpose);
                        sendBundle.putString("PAYMENTCOST", transmittedCost);
                        sendBundle.putString("FLATID", transmittedFlatID);
                        sendBundle.putString("PAYMENTID", transmittedPaymentID);
                        intent.putExtras(sendBundle);
                        startActivity(intent);
                    }
                });

        // interaction
        builder.setNegativeButton("Löschen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Bundle receiverBundle = getArguments();
                String transmittedPaymentID = receiverBundle.getString("PAYMENTID", "");
                DialogListener activity = (DialogListener) getActivity();
                activity.onReturnValue(transmittedPaymentID);
                dismiss();
            }
        });
        return builder.create();
    }

    // interface method
    @Override
    public void onReturnValue(String id) {
    }
}
