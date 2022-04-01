package Presenter.Registration;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;

import Model.RegistrationModel;

public class RegistrationPresenter implements RegistrationContract.Presenter, RegistrationContract.onRegistrationListener{
    private RegistrationContract.View mRegisterView;
    private RegistrationModel mRegistrationModel;

    public RegistrationPresenter(RegistrationContract.View registerView){
        this.mRegisterView = registerView;
        mRegistrationModel = new RegistrationModel(this);
    }

    @Override
    public void register(Activity activity, String email, String password, String name) {
        Log.d("email", email.toString());
        Log.d("password", password.toString());
        Log.d("name", name.toString());
        mRegistrationModel.performFirebaseRegistration(activity, email, password, name);
    }

    @Override
    public void onSuccess(FirebaseUser firebaseUser) {
        mRegisterView.onRegistrationSuccess(firebaseUser);
    }

    @Override
    public void onFailure(String message) {
        mRegisterView.onRegistrationFailure(message);
    }
}
