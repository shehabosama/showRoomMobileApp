package com.android.carview.ControlPanelFragment;

import com.android.carview.common.model.MainResponse;
import com.android.carview.common.model.UserList;
import com.android.carview.common.model.UserListResponse;
import com.android.carview.common.network.WebService;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterControl implements ControlContract.Presenter {

    ControlContract.View view;
    ControlContract.Model.onFinishedListener model;

    public PresenterControl(ControlContract.View view, ControlContract.Model.onFinishedListener model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void performGetAllUser() {
        view.showProgress();
        WebService.getInstance(false).getApi().getAllUsers().enqueue(new Callback<List<UserList>>() {
            @Override
            public void onResponse(Call<List<UserList>> call, Response<List<UserList>> response) {
                UserListResponse userListResponse = new UserListResponse();
                userListResponse.setUserLists(response.body());
                model.loadUserList(userListResponse);
            }

            @Override
            public void onFailure(Call<List<UserList>> call, Throwable t) {
                view.hideProgress();
                model.onFailuer(t);
            }
        });

    }

    @Override
    public void performDeleteUser(String delete, int id) {
        view.showProgress();
        WebService.getInstance(false).getApi().deleteUser(delete,id).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                model.onFinished(response.body().message);
                view.hideProgress();
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                view.hideProgress();
                model.onFailuer(t);
            }
        });
    }

    @Override
    public void performPermissionUser(String permission, int id, String value) {
        view.showProgress();
        WebService.getInstance(false).getApi().updatePermissionAdmin(permission,id,value).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                model.onFinished(response.body().message);
                view.hideProgress();
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                view.hideProgress();
                model.onFailuer(t);
            }
        });
    }

    @Override
    public void performBlockUser(int id, int value) {
        view.showProgress();
        WebService.getInstance(false).getApi().blockUser(value,id).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                model.onFinished(response.body().message);
                view.hideProgress();
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                view.hideProgress();
                model.onFailuer(t);
            }
        });
    }

}
