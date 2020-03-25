package com.android.carview.EmergencyNumbersFragment;

import android.text.TextUtils;
import android.util.Log;

import com.android.carview.common.model.EmergenceResponse;
import com.android.carview.common.model.MainResponse;
import com.android.carview.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jaiselrahman.filepicker.activity.FilePickerActivity.TAG;

public class PresenterEmergence implements EmergenceContract.Presenter {

    EmergenceContract.Model.onFinishedListener mModel;
    EmergenceContract.View mView;

    public PresenterEmergence(EmergenceContract.Model.onFinishedListener mModel, EmergenceContract.View mView) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void performAdd(String name, String number) {
        mView.showProgress();
        if(TextUtils.isEmpty(name)||TextUtils.isEmpty(number)){
            mModel.onFinished("please fill all fields");
        }else{
            WebService.getInstance(false).getApi().uploadEmerNumber(name,number).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: "+t.getLocalizedMessage() );
                }
            });
        }

    }

    @Override
    public void performGetEmergenceNo() {
        mView.showProgress();

        WebService.getInstance(false).getApi().getEmergencies().enqueue(new Callback<EmergenceResponse>() {
            @Override
            public void onResponse(Call<EmergenceResponse> call, Response<EmergenceResponse> response) {
                mModel.loadEmergenceList(response.body());
                mView.hideProgress();
            }

            @Override
            public void onFailure(Call<EmergenceResponse> call, Throwable t) {
                mView.hideProgress();
            }
        });
    }
}
