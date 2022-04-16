package Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ShoppingItemDialog extends AppCompatDialogFragment implements DialogListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit ShoppingListItem")
                .setMessage("Edit or delete this item")
                .setPositiveButton("edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Todo: edit item
                    }
                });

        builder.setNegativeButton("delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Bundle receiverBundle = getArguments();
                String transmittedItemID = receiverBundle.getString("ITEMID", "");
                DialogListener activity = (DialogListener) getActivity();
                activity.onReturnValue(transmittedItemID);
                dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onReturnValue(String id) {
    }
}
