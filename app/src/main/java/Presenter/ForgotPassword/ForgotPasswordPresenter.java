package Presenter.ForgotPassword;

import android.app.Activity;

import Model.ForgotPasswordModel;

public class ForgotPasswordPresenter implements ForgotPasswordContract.Presenter,ForgotPasswordContract.onPassRestListener {

    private ForgotPasswordContract.View mPasswordView;
    private ForgotPasswordModel mPasswordModel;

    public ForgotPasswordPresenter(ForgotPasswordContract.View mPasswordView){
        this.mPasswordView = mPasswordView;
        mPasswordModel = new ForgotPasswordModel(this);
    }

    @Override
    public void passReset(Activity activity, String email) {
        mPasswordModel.passwordReset(activity, email);
    }

    @Override
    public void onSuccess(String message) {
        mPasswordView.onPassResetSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        mPasswordView.onPassResetFailure(message);
    }
}
