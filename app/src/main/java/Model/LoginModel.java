package Model;
import android.app.Activity;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import Presenter.Login.LoginContract;

public class LoginModel implements LoginContract.Interactor {

    // MVP components
    private LoginContract.onLoginListener mOnLoginListener;

    public LoginModel(LoginContract.onLoginListener onLoginListener){
        this.mOnLoginListener = onLoginListener;
    }

    // OnCompleteListener looks great on the UI, but seems a bit bulky
    @Override
    public void performFirebaseLogin(Activity activity, String email, String password){
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mOnLoginListener.onSuccess(task.getResult().toString());
                        } else {
                            mOnLoginListener.onFailure(task.getException().toString());
                        }
                    }
                });
    }
}
