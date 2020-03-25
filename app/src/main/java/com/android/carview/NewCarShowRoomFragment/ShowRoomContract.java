package com.android.carview.NewCarShowRoomFragment;

import com.android.carview.common.model.CarResponse;
import com.android.carview.common.model.ShowRoom;
import com.android.carview.common.model.ShowRoomResponse;

public interface ShowRoomContract
{
    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void onFailuer(Throwable t);
            void loadShowRoomList(ShowRoomResponse showRoom);
        }
    }
    interface View{
        void showProgress();
        void hideProgress();

    }
    interface Presenter{
        void performGetShowRoom(String category);
        void performDeleteCar(String carId);
        void performDeleteShowRoom(String showRoom);
    }
}
