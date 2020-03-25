package com.android.carview.NewCarFragment;

import android.content.Context;
import android.net.Uri;

import com.android.carview.common.model.CarResponse;
import com.android.carview.common.model.DepartmentResponse;
import com.android.carview.common.model.FavoritesListResponse;
import com.android.carview.common.model.UploadResult;

import java.util.List;

public interface NewCarContract {

    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void onFailuer(Throwable t);
            void loadNewCarList(CarResponse carResponse,FavoritesListResponse favoritesListResponse);
            //void loadFavoritesCarList();
        }
    }
    interface View{
        void showProgress();
        void hideProgress();



    }
    interface Presenter{
        void performGetNewCar(String category,String userId);
        void performGetUsedCar(String category,String userId);
        void performAddItemFavorite(String userId,String carId);
        void performDeleteItemFavorite(String userId,String carId,String delete);
        void performGetUsedFavoriteCar(String user_id, CarResponse carResponses);
        void performDeleteCarItem(String carId);
    }
}
