package Presenter.ForgotPassword;

import android.app.Activity;

import Model.ForgotPasswordModel;

public class ForgotPasswordPresenter implements ForgotPasswordContract.Presenter,ForgotPasswordContract.onPassRestListener {

    // MVP components
    private ForgotPasswordContract.View mPasswordView;
    private ForgotPasswordModel mPasswordModel;

    public ForgotPasswordPresenter(ForgotPasswordContract.View mPasswordView){
        this.mPasswordView = mPasswordView;
        mPasswordModel = new ForgotPasswordModel(this);
    }

    // Presenter -> Model
    @Override
    public void passReset(Activity activity, String email) {
        mPasswordModel.passwordReset(activity, email);
    }

    // When Model is successful
    @Override
    public void onSuccess(String message) {
        mPasswordView.onPassResetSuccess(message);
    }

    // When Model failed
    @Override
    public void onFailure(String message) {
        mPasswordView.onPassResetFailure(message);
    }
}
