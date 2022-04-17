package Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import View.After.ActivityPaymentOverview;
import View.After.ActivityShoppingList;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.R;

public class BasketDialog extends AppCompatDialogFragment implements DialogListener{

    // This dialog is the middle man between two activities
    // The business logic is inside the mvp classes of ActivityBasketList and ActivityShoppingList

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // initializing
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setTitle("Einkauf bearbeiten")
                .setMessage("Bearbeite oder lösche einen Einkauf")
                .setPositiveButton("Bearbeiten", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bundle receiverBundle = getArguments();
                        String transmittedData = receiverBundle.getString("BASKETDATA", "");
                        String transmittedBasketID = receiverBundle.getString("BASKETID", "");
                        Intent intent = new Intent(getContext(), ActivityShoppingList.class);
                        Bundle sendBundle = new Bundle();
                        sendBundle.putBoolean("STATE", true);
                        sendBundle.putString("BASKETDATA", transmittedData);
                        sendBundle.putString("BASKETID", transmittedBasketID);
                        intent.putExtras(sendBundle);
                        startActivity(intent);
                    }
                });

        // interaction
        builder.setNegativeButton("Löschen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Bundle receiverBundle = getArguments();
                String transmittedBasketID = receiverBundle.getString("BASKETID", "");
                DialogListener activity = (DialogListener) getActivity();
                activity.onReturnValue(transmittedBasketID);
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
