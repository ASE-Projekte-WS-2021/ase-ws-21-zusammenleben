package Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.R;

import Presenter.UserProfile.UserProfilePresenter;

public class UserProfileDialog extends AppCompatDialogFragment implements DialogListener{


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setTitle("WG verlassen")
                .setMessage("Willst du das wirklich tun?")
                .setPositiveButton("ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DialogListener activity = (DialogListener) getActivity();
                        activity.onReturnValue("true");
                        dismiss();
                    }
                })
        ;
        return builder.create();
    }

    @Override
    public void onReturnValue(String id) {

    }
}
