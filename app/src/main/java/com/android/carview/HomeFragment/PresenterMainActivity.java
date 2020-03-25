package com.android.carview.HomeFragment;



import com.android.carview.common.model.LoginResponse;
import com.android.carview.common.model.User;
import com.android.carview.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterMainActivity implements MainContract.Presenter {
    MainContract.Model.onFinishedListener mModel;
    MainContract.View mView;

    public PresenterMainActivity(MainContract.Model.onFinishedListener mModel, MainContract.View mView) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void performCheckIsBlocked(String userId) {

        User user = new User();
        user.id = Integer.parseInt(userId);
        WebService.getInstance(false).getApi().checkBlocked(user).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                mModel.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }
}
