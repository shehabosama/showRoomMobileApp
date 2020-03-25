package com.android.carview.SettingsFragment;

import android.text.TextUtils;

import com.android.carview.common.SqlHelper.myDbAdapter;
import com.android.carview.common.model.MainResponse;
import com.android.carview.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterSettings implements SettingsContract.Presenter {
    private SettingsContract.View mView;
    private SettingsContract.Model.onFinishedListener mModel;
    private myDbAdapter myDbAdapter;
    public PresenterSettings(SettingsContract.Model.onFinishedListener mModel, SettingsContract.View mView ,myDbAdapter myDbAdapter) {
        this.mView = mView;
        this.mModel = mModel;
        this.myDbAdapter  = myDbAdapter;
    }


    @Override
    public void updateInformationWithPass(String userId, final String userName, final String userAddress, String userPassword, final String email) {
        mView.showProgress();
        if(TextUtils.isEmpty(userId)||TextUtils.isEmpty(userName)||TextUtils.isEmpty(userAddress)||TextUtils.isEmpty(email)||TextUtils.isEmpty(userPassword)){
            mView.hideProgress();
            mModel.onFinished("Please fill all fields..");
        }else{
            WebService.getInstance(false).getApi().updateUserInformation(Integer.parseInt(userId),userName,userAddress,userPassword).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);
                    myDbAdapter.updateName(email,userName,userAddress);
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    mView.hideProgress();
                }
            });
        }

    }

    @Override
    public void upadteInformation(String userId, final String userName, final String userAddress, final String email) {
        mView.showProgress();
        if(TextUtils.isEmpty(userId)||TextUtils.isEmpty(userName)||TextUtils.isEmpty(userAddress)||TextUtils.isEmpty(email)){
            mView.hideProgress();
            mModel.onFinished("Please fill all fields..");
        }
        WebService.getInstance(false).getApi().updateUserInformation(Integer.parseInt(userId),userName,userAddress).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                mModel.onFinished(response.body().message);
                myDbAdapter.updateName(email,userName,userAddress);
                mView.hideProgress();
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                mView.hideProgress();
            }
        });
    }
}