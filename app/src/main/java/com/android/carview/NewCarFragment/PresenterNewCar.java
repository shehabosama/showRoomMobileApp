package com.android.carview.NewCarFragment;

import android.text.TextUtils;
import android.util.Log;

import com.android.carview.common.model.CarResponse;
import com.android.carview.common.model.FavoritesListResponse;
import com.android.carview.common.model.MainResponse;
import com.android.carview.common.network.WebService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jaiselrahman.filepicker.activity.FilePickerActivity.TAG;

public class PresenterNewCar implements NewCarContract.Presenter {
    NewCarContract.Model.onFinishedListener mModel;
    NewCarContract.View mView;

    public PresenterNewCar(NewCarContract.Model.onFinishedListener mModel, NewCarContract.View mView) {
        this.mModel = mModel;
        this.mView = mView;
    }


    @Override
    public void performGetNewCar(String category , final String userId) {
        mView.showProgress();
        if(TextUtils.isEmpty(category)){
            mModel.onFinished("some thing went wrong");
        }else {
            WebService.getInstance(false).getApi().getCarCategory(Integer.parseInt(category)).enqueue(new Callback<CarResponse>() {
                @Override
                public void onResponse(Call<CarResponse> call, Response<CarResponse> response) {
                    performGetUsedFavoriteCar(userId,response.body());
                  //  mModel.loadNewCarList(response.body());

                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<CarResponse> call, Throwable t) {

                    Log.e(TAG, "onFailure: "+t.getLocalizedMessage() );
                    mView.hideProgress();

                }
            });
        }


    }

    @Override
    public void performGetUsedCar(String category, final String userId) {
        mView.showProgress();
        if(TextUtils.isEmpty(category)){
            mModel.onFinished("some thing went wrong");
        }else {
            WebService.getInstance(false).getApi().getCarCategory(Integer.parseInt(category)).enqueue(new Callback<CarResponse>() {
                @Override
                public void onResponse(Call<CarResponse> call, Response<CarResponse> response) {
                    performGetUsedFavoriteCar(userId,response.body());

                    Log.e(TAG, "onResponse: "+response.body().getCar() );
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<CarResponse> call, Throwable t) {

                    Log.e(TAG, "onFailure: "+t.getLocalizedMessage() );
                    mView.hideProgress();

                }
            });
        }
    }

    @Override
    public void performAddItemFavorite(String userId, String carId) {
        mView.showProgress();
        if(TextUtils.isEmpty(userId)||TextUtils.isEmpty(carId)){
            mModel.onFinished("Something Went Wrong");
            mView.hideProgress();
        }else{
            WebService.getInstance(false).getApi().addFevoriteCar(Integer.parseInt(userId),Integer.parseInt(carId)).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mView.hideProgress();
                    mModel.onFinished("Add to Favorites Successfully");
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
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

    @Override
    public void performGetUsedFavoriteCar(String userId, final CarResponse carResponses) {
        mView.showProgress();

        if(TextUtils.isEmpty(userId)){
            mModel.onFinished("Please Make sure are you blocked or not");
            mView.hideProgress();
        }else{
            WebService.getInstance(false).getApi().getAllFavorites(Integer.parseInt(userId)).enqueue(new Callback<FavoritesListResponse>() {
                @Override
                public void onResponse(Call<FavoritesListResponse> call, Response<FavoritesListResponse> response) {
                  //  mModel.loadFavoritesCarList(response.body());
                    mModel.loadNewCarList(carResponses,response.body());
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
    public void performDeleteCarItem(String carId) {
        mView.showProgress();
        if(TextUtils.isEmpty(carId)){
            mModel.onFinished("this car not found.. please make sure you are update this activity");
            mView.hideProgress();
        }else{
            WebService.getInstance(false).getApi().deleteCarItem(Integer.parseInt(carId)).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    mView.hideProgress();
                }
            });
        }
    }
}
