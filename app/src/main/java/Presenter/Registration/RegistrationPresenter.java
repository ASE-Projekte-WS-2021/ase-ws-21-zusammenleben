package Presenter.Registration;

import android.app.Activity;
import com.google.firebase.auth.FirebaseUser;

import Model.RegistrationModel;

public class RegistrationPresenter implements RegistrationContract.Presenter, RegistrationContract.onRegistrationListener{

    // MVP components
    private RegistrationContract.View mRegisterView;
    private RegistrationModel mRegistrationModel;

    public RegistrationPresenter(RegistrationContract.View registerView){
        this.mRegisterView = registerView;
        mRegistrationModel = new RegistrationModel(this);
    }

    // Presenter -> Model
    @Override
    public void register(Activity activity, String email, String password, String name) {
        mRegistrationModel.performFirebaseRegistration(activity, email, password, name);
    }

    // When Model is successful
    @Override
    public void onSuccess(FirebaseUser firebaseUser) {
        mRegisterView.onRegistrationSuccess(firebaseUser);
    }

    // When Firebase failed
    @Override
    public void onFailure(String message) {
        mRegisterView.onRegistrationFailure(message);
    }
}
