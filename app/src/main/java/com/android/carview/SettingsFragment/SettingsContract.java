package com.android.carview.SettingsFragment;

public interface SettingsContract {

    interface Model {
        interface onFinishedListener {
            void onFinished(String result);

            void onFailuer(Throwable t);

        }
    }

    interface View {
        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void updateInformationWithPass(String userId,String userName,String userAddress,String userPassword,String email);
        void upadteInformation(String userId, String userName ,String userAddress ,String email);
    }

}
