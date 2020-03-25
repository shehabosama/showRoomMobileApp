package com.android.carview.FavoritesFragment;

import com.android.carview.common.model.CarResponse;
import com.android.carview.common.model.FavoritesListResponse;

public interface FavoritesContract {
    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void onFailuer(Throwable t);
            void loadFavoritesCarList(FavoritesListResponse favoritesListResponse);
        }
    }
    interface View{
        void showProgress();
        void hideProgress();



    }
    interface Presenter{
        void performGetUsedCar(String user_id);
        void performDeleteItemFavorite(String userId,String carId,String delete);

    }
}
