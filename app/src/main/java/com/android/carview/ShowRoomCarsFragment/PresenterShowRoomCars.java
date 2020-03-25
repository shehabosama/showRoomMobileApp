package com.android.carview.ShowRoomCarsFragment;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import com.android.carview.common.helper.FileUtil;
import com.android.carview.common.model.MainResponse;
import com.android.carview.common.model.ShowRoomCarResponse;
import com.android.carview.common.network.WebService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PresenterShowRoomCars implements ShowRoomCarsContract.Presenter {
    private ShowRoomCarsContract.Model.onFinishedListener mModle;
    private ShowRoomCarsContract.View mView;

    public PresenterShowRoomCars(ShowRoomCarsContract.Model.onFinishedListener mModle, ShowRoomCarsContract.View mView) {
        this.mModle = mModle;
        this.mView = mView;
    }


    @Override
    public void performGetShowRoomCars(String showRoomId) {
        mView.showProgress();
        if(TextUtils.isEmpty(showRoomId)){
            mModle.onFinished("something went wrong...");
            mView.hideProgress();
        }else{
            WebService.getInstance(false).getApi().getShowRoomCars(Integer.parseInt(showRoomId)).enqueue(new Callback<ShowRoomCarResponse>() {
                @Override
                public void onResponse(Call<ShowRoomCarResponse> call, Response<ShowRoomCarResponse> response) {
                    mModle.loadShowRoomCarsList(response.body());
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<ShowRoomCarResponse> call, Throwable t) {
                    mModle.onFinished(t.getLocalizedMessage());
                    mView.hideProgress();
                }
            });
        }
    }

    @Override
    public void performAddShowRoomCar(Context context,List<Uri> uris, String show_room_id, String car_name, String car_description, String car_model) {
        mView.showProgress();
        if(TextUtils.isEmpty(show_room_id)||TextUtils.isEmpty(car_name)||TextUtils.isEmpty(car_description)||TextUtils.isEmpty(car_model)){
            mModle.onFinished("Something Went Wrong...");
            mView.hideProgress();
        }else if (uris.isEmpty()){
            mModle.onFinished("please Select photo first..");
        }else{
            List<MultipartBody.Part> list = new ArrayList<>();
            int i = 0;
            for (Uri uri : uris) {
                File file = new File( FileUtil.getPath(uri,context));
                //very important files[]
                final RequestBody requestFile =
                        RequestBody.create(
                                MediaType.parse("image/jpg"),
                                file
                        );
                MultipartBody.Part imageRequest = MultipartBody.Part.createFormData("files[]", file.getName(), requestFile);
                list.add(imageRequest);
            }
            WebService.getInstance(false).getApi().addShowRoomCar(list,Integer.parseInt(show_room_id),car_name,car_description,Integer.parseInt(car_model))
                    .enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    assert response.body() != null;
                    mModle.onFinished(response.body().message);
                    mModle.onFinishedAddCar(response.body().message);
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: "+t.getLocalizedMessage() );
                }
            });
        }
    }

    @Override
    public void performDeleteCar(String carId) {
        if(TextUtils.isEmpty(carId)){
            mModle.onFinished("please make sure this car still exist");
        }else{
            WebService.getInstance(true).getApi().deleteShowRoomCarItem(Integer.parseInt(carId)).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModle.onFinished(response.body().message);
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: "+t.getMessage() );
                }
            });
        }
    }
}
