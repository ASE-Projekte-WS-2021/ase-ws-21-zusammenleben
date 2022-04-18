package Presenter.ForgotPassword;
import android.app.Activity;

public interface ForgotPasswordContract {

    interface View{
        void onPassResetSuccess(String message);
        void onPassResetFailure(String message);
    }

    interface Presenter{
        void passReset(Activity activity, String email);
    }

    interface Interactor{
        void passwordReset(Activity activity, String email);
    }

    interface onPassRestListener{
        void onSuccess(String message);
        void onFailure(String message);
    }
}
