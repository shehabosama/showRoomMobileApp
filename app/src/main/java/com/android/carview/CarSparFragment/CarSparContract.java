package com.android.carview.CarSparFragment;

import com.android.carview.common.model.CarResponse;
import com.android.carview.common.model.SparCar;

import java.util.List;

public interface CarSparContract
{
    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void onFailuer(Throwable t);
            void loadNewCarSparList(List<SparCar> sparCar);
        }
    }
    interface View{
        void showProgress();
        void hideProgress();



    }
    interface Presenter{
        void performGetAllCarSpar();
        void performDeleteCarSpar(String spar_id);

    }
}
