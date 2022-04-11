package Utils;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.R;

public class ShoppingListDialog extends AppCompatDialogFragment implements DialogListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceBundle){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Hinzufügen");

        View dialogLayout = LayoutInflater.from(getActivity()).inflate(R.layout.layout_item_dialog, null, false);

        builder.setView(dialogLayout);
        final EditText inputItem = dialogLayout.findViewById(R.id.inputItem);
        final EditText costItem = dialogLayout.findViewById(R.id.costItem);
        final EditText numItem = dialogLayout.findViewById(R.id.numItem);

        builder.setPositiveButton("Hinzufügen", (dialog, which) -> {
            if (!inputItem.getText().toString().isEmpty() && !costItem.getText().toString().isEmpty() && !numItem.getText().toString().isEmpty()) {
                if (Integer.parseInt(numItem.getText().toString()) == 1){
                    list.add(inputItem.getText().toString().trim());
                    listcosts.add(costItem.getText().toString().trim());
                    arrayAdapter.notifyDataSetChanged();
                    arrayAdapterCosts.notifyDataSetChanged();
                    addSums();
                }

                else if(Integer.parseInt(numItem.getText().toString()) > 1) {
                    int newCost = Integer.parseInt(costItem.getText().toString());
                    int newNum = Integer.parseInt(numItem.getText().toString());
                    int result = newCost * newNum;

                    String resultStr = Integer.toString(result);
                    costItem.setText(resultStr);

                    list.add(inputItem.getText().toString().trim());
                    listcosts.add(costItem.getText().toString().trim());
                    arrayAdapter.notifyDataSetChanged();
                    arrayAdapterCosts.notifyDataSetChanged();
                    addSums();
                }

            } else {
                inputItem.setError("add item here !");
                costItem.setError("add costs here !");
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
