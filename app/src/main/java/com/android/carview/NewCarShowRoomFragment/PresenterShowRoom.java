package com.android.carview.NewCarShowRoomFragment;

import android.text.TextUtils;
import android.util.Log;

import com.android.carview.common.model.MainResponse;
import com.android.carview.common.model.ShowRoomResponse;
import com.android.carview.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PresenterShowRoom implements ShowRoomContract.Presenter {
    ShowRoomContract.Model.onFinishedListener mModel;
    ShowRoomContract.View mView;

    public PresenterShowRoom(ShowRoomContract.Model.onFinishedListener mModel, ShowRoomContract.View mView) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void performGetShowRoom(String category) {
        mView.showProgress();
        if(TextUtils.isEmpty(category)){
            mModel.onFinished("something went wrong");
            mView.hideProgress();
        }else{
            WebService.getInstance(false).getApi().getShowRoomCategory(Integer.parseInt(category)).enqueue(new Callback<ShowRoomResponse>() {
                @Override
                public void onResponse(Call<ShowRoomResponse> call, Response<ShowRoomResponse> response) {
                    mModel.loadShowRoomList(response.body());
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<ShowRoomResponse> call, Throwable t) {
                    mView.hideProgress();
                }
            });
        }
    }

    @Override
    public void performDeleteCar(String carId) {
        if(TextUtils.isEmpty(carId)){
            mModel.onFinished("please make sure this car still exist");
        }else{
            WebService.getInstance(true).getApi().deleteShowRoomCarItem(Integer.parseInt(carId)).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: "+t.getMessage() );
                }
            });
        }
    }

    @Override
    public void performDeleteShowRoom(String showRoom) {
        if(TextUtils.isEmpty(showRoom)){
            mModel.onFinished("Something Went Wrong please try again");
        }else{
            WebService.getInstance(true).getApi().deleteShowRoomItem(Integer.parseInt(showRoom)).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: "+t.getMessage() );
                }
            });
        }
    }
}
