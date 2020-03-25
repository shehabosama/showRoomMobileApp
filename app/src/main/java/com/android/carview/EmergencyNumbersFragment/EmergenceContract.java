package com.android.carview.EmergencyNumbersFragment;

import com.android.carview.Login.LoginContract;
import com.android.carview.common.model.EmergenceResponse;
import com.android.carview.common.model.User;

public interface EmergenceContract
{
    interface Model{
        interface onFinishedListener {
            void onFinished(String result);
            void onFailuer(Throwable t);
            void loadEmergenceList(EmergenceResponse response);
        }
    }


interface View{
    void showProgress();
    void hideProgress();

}
interface Presenter{
    void performAdd(String name, String number);
    void performGetEmergenceNo();
}
}
