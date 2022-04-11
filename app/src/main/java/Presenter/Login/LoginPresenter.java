package Presenter.Login;

import android.app.Activity;

import Model.LoginModel;

public class LoginPresenter implements LoginContract.Presenter, LoginContract.onLoginListener {
    private LoginContract.View mLoginView;
    private LoginModel mLoginModel;

    public LoginPresenter(LoginContract.View mLoginView){
        this.mLoginView = mLoginView;
        mLoginModel = new LoginModel(this);
    }

    @Override
    public void login(Activity activity, String email, String password) {
        mLoginModel.performFirebaseLogin(activity, email, password);
    }

    @Override
    public void onSuccess(String message) {
        mLoginView.onLoginSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        mLoginView.onLoginFailure(message);
    }
}
