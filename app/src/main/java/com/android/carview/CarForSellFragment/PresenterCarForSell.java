package com.android.carview.CarForSellFragment;

import android.text.TextUtils;
import android.util.Log;

import com.android.carview.NewCarFragment.NewCarContract;
import com.android.carview.common.model.CarResponse;
import com.android.carview.common.model.MainResponse;
import com.android.carview.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jaiselrahman.filepicker.activity.FilePickerActivity.TAG;

public class PresenterCarForSell implements CarForSellContract.Presenter {
    CarForSellContract.Model.onFinishedListener mModel;
    CarForSellContract.View mView;

    public PresenterCarForSell(CarForSellContract.Model.onFinishedListener mModel, CarForSellContract.View mView) {
        this.mModel = mModel;
        this.mView = mView;
    }
    @Override
    public void performGetAllCar() {
        mView.showProgress();

            WebService.getInstance(false).getApi().getAllCar().enqueue(new Callback<CarResponse>() {
                @Override
                public void onResponse(Call<CarResponse> call, Response<CarResponse> response) {
                    mModel.loadNewCarList(response.body());
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

