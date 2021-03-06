package Model;
import android.app.Activity;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import Presenter.ForgotPassword.ForgotPasswordContract;

public class ForgotPasswordModel implements ForgotPasswordContract.Interactor {

    // MVP components
    private ForgotPasswordContract.onPassRestListener mOnPassResetListener;

    public ForgotPasswordModel(ForgotPasswordContract.onPassRestListener onPassRestListener){
        this.mOnPassResetListener = onPassRestListener;
    }

    // Using Firebase functions, Model -> Firebase
    @Override
    public void passwordReset(Activity activity, String email){
        FirebaseAuth.getInstance()
                .sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mOnPassResetListener.onSuccess("Erfolgreich");
                        } else {
                            mOnPassResetListener.onFailure("Fehlgeschlagen");
                        }
                    }
                });
    }
}
