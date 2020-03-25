package com.android.carview.CarForSellFragment;

import com.android.carview.common.model.CarResponse;

public interface CarForSellContract {
    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void onFailuer(Throwable t);
            void loadNewCarList(CarResponse carResponse);
        }
    }
    interface View{
        void showProgress();
        void hideProgress();



    }
    interface Presenter{
        void performGetAllCar();
        void performDeleteCarItem(String carId);
    }
}
