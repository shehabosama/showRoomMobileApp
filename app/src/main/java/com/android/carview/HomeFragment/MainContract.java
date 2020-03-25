package com.android.carview.HomeFragment;

import com.android.carview.common.model.LoginResponse;


public interface MainContract
{
    interface Model {
        interface onFinishedListener {
            void onFinished(LoginResponse loginResponse);
            void onFailuer(Throwable t);
        }

    }
    interface View {
        void showProgress();

        void hideProgress();
    }
    interface Presenter{
        void performCheckIsBlocked(String userId);
    }
}
