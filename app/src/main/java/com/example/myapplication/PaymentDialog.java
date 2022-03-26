package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatDialogFragment;

public class PaymentDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Payment")
                .setMessage("Edit or delete this payment")
                .setPositiveButton("edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("button", "edit");
                        Bundle receiverBundle = getArguments();
                        String paymentData = receiverBundle.getString("PAYMENT", "");
                        Intent intent = new Intent(getContext(), ActivityEditPayment.class);
                        Bundle sendBundle = new Bundle();
                        sendBundle.putString("PAYMENT", paymentData);
                        intent.putExtras(sendBundle);
                        startActivity(intent);
                    }
                });

        builder.setNegativeButton("delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("button", "delete");
            }
        });
        return builder.create();
    }

}
