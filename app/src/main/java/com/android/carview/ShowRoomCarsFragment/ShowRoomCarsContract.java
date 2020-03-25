package com.android.carview.ShowRoomCarsFragment;

import android.content.Context;
import android.net.Uri;

import com.android.carview.common.model.CarResponse;
import com.android.carview.common.model.ShowRoomCarResponse;

import java.util.List;

public interface ShowRoomCarsContract
{
    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void onFailuer(Throwable t);
            void loadShowRoomCarsList(ShowRoomCarResponse carResponse);

            void onFinishedAddCar(String message);
        }
    }
    interface View{
        void showProgress();
        void hideProgress();



    }
    interface Presenter{
        void performGetShowRoomCars(String showRoomId);
        void performAddShowRoomCar(Context context,
                                   List<Uri> uris,
                                   String show_room_id,
                                   String car_name,
                                   String car_description,
                                   String car_model);
        void performDeleteCar(String carId);

    }
}
