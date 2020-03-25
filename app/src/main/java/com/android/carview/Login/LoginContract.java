package com.android.carview.Login;

import com.android.carview.common.model.User;

public interface LoginContract {
    interface Model {
        interface onFinishedListener {
            void onFinished(String user);
            void onFailuer(Throwable t);
        }
        void Login(onFinishedListener onFinishedListener, String email, String password);
    }
    interface View{
        void showProgress();
        void hideProgress();
        void loginValidations();
        void loginSuccess();
        void loginError();
        void emailInvalid();

        void setUserInfo(User user);
    }
    interface Presenter{
        void performLogin(String email, String password);
    }
}
