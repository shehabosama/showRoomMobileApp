package com.android.carview.FavoritesFragment;

import android.text.TextUtils;

import com.android.carview.common.model.FavoritesListResponse;
import com.android.carview.common.model.MainResponse;
import com.android.carview.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterFavorites implements FavoritesContract.Presenter {
    FavoritesContract.Model.onFinishedListener mModel;
    FavoritesContract.View mView;

    public PresenterFavorites(FavoritesContract.Model.onFinishedListener mModel, FavoritesContract.View mView) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void performGetUsedCar(String userId) {
        mView.showProgress();

        if(TextUtils.isEmpty(userId)){
            mModel.onFinished("Please Make sure are you blocked or not");
            mView.hideProgress();
        }else{
            WebService.getInstance(false).getApi().getAllFavorites(Integer.parseInt(userId)).enqueue(new Callback<FavoritesListResponse>() {
                @Override
                public void onResponse(Call<FavoritesListResponse> call, Response<FavoritesListResponse> response) {
                    mModel.loadFavoritesCarList(response.body());
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<FavoritesListResponse> call, Throwable t) {
                    mModel.onFinished(t.getLocalizedMessage());
                }
            });
        }
    }
    @Override
    public void performDeleteItemFavorite(String userId, String carId, String delete) {
        mView.showProgress();
        if(TextUtils.isEmpty(userId)||TextUtils.isEmpty(carId)){
            mModel.onFinished("Something Went Wrong");
            mView.hideProgress();
        }else{
            WebService.getInstance(false).getApi().addFevoriteCar(Integer.parseInt(userId),Integer.parseInt(carId),delete).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mView.hideProgress();
                    mModel.onFinished("Delete to Favorites Successfully");
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    mModel.onFinished(t.getLocalizedMessage());
                }
            });
        }
    }
}
