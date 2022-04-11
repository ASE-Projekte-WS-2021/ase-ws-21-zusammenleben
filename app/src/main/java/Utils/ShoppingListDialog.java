package Utils;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;

import java.util.Objects;

import View.After.ActivityShoppingList;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ShoppingListDialog extends AppCompatDialogFragment implements DialogListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceBundle){

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Hinzufügen");

        View dialogLayout = LayoutInflater.from(getActivity()).inflate(R.layout.layout_item_dialog, null, false);

        builder.setView(dialogLayout);
        final EditText inputItem = dialogLayout.findViewById(R.id.inputItem);
        final EditText numItem = dialogLayout.findViewById(R.id.numItem);

        builder.setPositiveButton("Hinzufügen", (dialog, which) -> {
            if (!inputItem.getText().toString().isEmpty() && !numItem.getText().toString().isEmpty()) {
                Bundle receiverBundle = getArguments();
                String transmittedItem = inputItem.getText().toString().trim();
                String transmittedAmount = numItem.getText().toString().trim();
                String transmittedItemID = receiverBundle.getString("ITEMID", "");
                Intent intent = new Intent(getContext(), ActivityShoppingList.class);
                Bundle sendBundle = new Bundle();
                sendBundle.putBoolean("STATE", true);
                sendBundle.putString("ITEM", transmittedItem);
                sendBundle.putString("AMOUNT", transmittedAmount);
                sendBundle.putString("ITEMID", transmittedItemID);
                intent.putExtras(sendBundle);
                startActivity(intent);
            } else {
                inputItem.setError("Bitte füge ein Item hinzu");
                numItem.setError("die Anzahl darf nicht null sein");
            }
        });

        builder.setNegativeButton("Abbrechen", (dialog, which) -> dialog.dismiss());
        builder.show();

        return builder.create();
    }

    @Override
    public void onReturnValue(String id) {

    }
}
