package Model;
import android.app.Activity;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Entities.User;
import Presenter.Registration.RegistrationContract;

public class RegistrationModel implements RegistrationContract.Interactor {

    // MVP components
    private RegistrationContract.onRegistrationListener mOnRegistrationListener;

    // Firebase
    private FirebaseDatabase database = FirebaseDatabase.getInstance(FIREBASEPATH);

    // Utils
    private static final String FIREBASEPATH = "https://wgfinance-b594f-default-rtdb.europe-west1.firebasedatabase.app/";
    private static final String USERPATH = "Users";

    public RegistrationModel(RegistrationContract.onRegistrationListener onRegistrationListener){
        this.mOnRegistrationListener = onRegistrationListener;
    }

    // Model -> Firebase. We tried an onCompleteListener with a task
    // This is more code, but arguably results in a cleaner interaction flow on the UI
    // The ValueEventListener would probably work too
    @Override
    public void performFirebaseRegistration(Activity activity, String email, String password, String name) {
        DatabaseReference ref = database.getReference(USERPATH);
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            mOnRegistrationListener.onFailure(task.getException().getMessage());
                        } else {
                            User user = new User(email, password);
                            ref.push().setValue(user);
                            mOnRegistrationListener.onSuccess(task.getResult().getUser());
                        }
                    }
                });
    }
}
