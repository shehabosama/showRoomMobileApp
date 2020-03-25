package com.android.carview.SellMyCarFragment;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.android.carview.common.helper.FileUtil;
import com.android.carview.common.model.MainResponse;
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

import static com.jaiselrahman.filepicker.activity.FilePickerActivity.TAG;

public class PresenterSellMyCar implements SellMyCarContract.Presenter {
    private SellMyCarContract.Model.onFinishedListener mModel;
    private SellMyCarContract.View mView;
    public PresenterSellMyCar (SellMyCarContract.Model.onFinishedListener mModel,SellMyCarContract.View mView){
        this.mModel = mModel;
        this.mView = mView;
    }
    @Override
    public void performPublish(List<Uri> uris, Context context, String userId, String carName, String carDescription, String carModel, String category,String price ,String sellPrice,String phone_no) {
        mView.showProgress();
        if(uris.size()<=0){
            mModel.onFinished("Please Select photo first");
            mView.hideProgress();
        }else if(TextUtils.isEmpty(category)||Integer.parseInt(category)==0) {
            mModel.onFinished("Please Select the category ....");
            mView.hideProgress();
        }else if (TextUtils.isEmpty(userId)){
            mModel.onFinished("Please make sure that you not blocked from this app");
            mView.hideProgress();
        }else if (TextUtils.isEmpty(carName) || TextUtils.isEmpty(carDescription) || TextUtils.isEmpty(String.valueOf(carModel))){
            mModel.onFinished("Please Make sure , you are fill all fields");
            mView.hideProgress();
        }else if (TextUtils.isEmpty(phone_no)){
            mModel.onFinished("please Write the phone number");
            mView.hideProgress();
        }else {
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


            WebService.getInstance(false).getApi().uploadImages(list,
                    Integer.parseInt(userId),
                    carName,carDescription,
                    Integer.parseInt(carModel),
                    Integer.parseInt(category),
                    Double.parseDouble(price),Double.parseDouble(sellPrice),phone_no).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    Log.e(TAG, "onResponse: "+t.getLocalizedMessage() );
                    mView.hideProgress();
                }
            });

        }
    }
}
