package Utils;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ShoppingListDialog extends AppCompatDialogFragment implements DialogListener {

    View dialogLayout;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceBundle){

        /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Hinzufügen");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogLayout = inflater.inflate(R.layout.layout_item_dialog, null, false);
        //Log.d("Bugfix1", dialogLayout.getParent().toString());

        if(dialogLayout.getParent() != null) {
            ((ViewGroup)dialogLayout.getParent()).removeView(dialogLayout); // <- fix
        }

        builder.setView(dialogLayout);
        EditText inputItem = dialogLayout.findViewById(R.id.inputItem);
        EditText numItem = dialogLayout.findViewById(R.id.numItem);

        builder.setPositiveButton("Hinzufügen", (dialog, which) -> {
            if (!inputItem.getText().toString().isEmpty() && !numItem.getText().toString().isEmpty()) {
                String transmittedItem = inputItem.getText().toString().trim();
                String transmittedAmount = numItem.getText().toString().trim();
                Intent intent = new Intent(getContext(), ActivityShoppingList.class);
                Bundle sendBundle = new Bundle();
                sendBundle.putString("ITEM", transmittedItem);
                sendBundle.putString("AMOUNT", transmittedAmount);
                intent.putExtras(sendBundle);
                startActivity(intent);
            } else {
                inputItem.setError("Bitte fügen Sie ein Item hinzu");
                numItem.setError("die Anzahl darf nicht null sein");
            }
        });

        builder.setNegativeButton("Abbrechen", (dialog, which) -> dialog.dismiss());
        builder.show();

        return builder.create();*/
        return null;
    }

    @Override
    public void onReturnValue(String id) {
    }
}
