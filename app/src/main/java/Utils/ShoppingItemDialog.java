package Utils;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.example.myapplication.R;

public class ShoppingItemDialog extends AppCompatDialogFragment implements DialogListener{

    // We decided that you cannot edit an existing item but only delete and (re-)create an item
    // This seems less bulky for the user. Deleting and recreating can be done in the same time as editing

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // initializing
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setTitle("Item löschen")
                .setMessage("Möchten Sie dieses Item löschen?");

        // interaction
        builder.setNegativeButton("löschen", new DialogInterface.OnClickListener() {
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

    // interface method
    @Override
    public void onReturnValue(String id) {
    }
}
