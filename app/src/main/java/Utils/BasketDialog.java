package Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import View.After.ActivityPaymentOverview;
import View.After.ActivityShoppingList;
import androidx.appcompat.app.AppCompatDialogFragment;

public class BasketDialog extends AppCompatDialogFragment implements DialogListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Basket")
                .setMessage("Edit or delete this basket")
                .setPositiveButton("edit", new DialogInterface.OnClickListener() {
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

        builder.setNegativeButton("delete", new DialogInterface.OnClickListener() {
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

    @Override
    public void onReturnValue(String id) {
    }
}
