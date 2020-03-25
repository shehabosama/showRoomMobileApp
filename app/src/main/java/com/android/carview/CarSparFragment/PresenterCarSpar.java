package com.android.carview.CarSparFragment;

import android.text.TextUtils;
import android.util.Log;

import com.android.carview.common.model.MainResponse;
import com.android.carview.common.model.SparCar;
import com.android.carview.common.network.WebService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PresenterCarSpar implements CarSparContract.Presenter{
    private CarSparContract.Model.onFinishedListener mModel;
    CarSparContract.View mView;

    public PresenterCarSpar(CarSparContract.Model.onFinishedListener mModel, CarSparContract.View mView) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void performGetAllCarSpar() {
        mView.showProgress();
        WebService.getInstance(false).getApi().getAllSparCar().enqueue(new Callback<List<SparCar>>() {
            @Override
            public void onResponse(Call<List<SparCar>> call, Response<List<SparCar>> response) {
                mModel.loadNewCarSparList(response.body());
                mView.hideProgress();
            }

            @Override
            public void onFailure(Call<List<SparCar>> call, Throwable t) {
                mModel.onFinished("Something Went wrong");
                mView.hideProgress();
            }
        });
    }

    @Override
    public void performDeleteCarSpar(String spar_id) {
        if(TextUtils.isEmpty(spar_id)){
            mModel.onFinished("Something Went Wrong");
        }else{
            WebService.getInstance(true).getApi().deleteSpareItem(Integer.parseInt(spar_id)).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: "+ t.getMessage() );
                }
            });
        }
    }
}
