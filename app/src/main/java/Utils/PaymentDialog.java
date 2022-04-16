package Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import View.After.ActivityPaymentOverview;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PaymentDialog extends AppCompatDialogFragment implements DialogListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

    @Override
    public void onReturnValue(String id) {
    }
}
