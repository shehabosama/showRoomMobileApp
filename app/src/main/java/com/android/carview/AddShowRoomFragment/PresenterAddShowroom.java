package com.android.carview.AddShowRoomFragment;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.android.carview.common.helper.FileUtil;
import com.android.carview.common.model.MainResponse;
import com.android.carview.common.model.UploadResult;
import com.android.carview.common.network.WebService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PresenterAddShowroom implements AddShowroomContract.Presenter {

    AddShowroomContract.Model.onFinishedListener mModel;
    AddShowroomContract.View mView;

    public PresenterAddShowroom(AddShowroomContract.Model.onFinishedListener mModel, AddShowroomContract.View mView) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void performAddShowRoom(Uri uri, Context context, String user_id, String show_room_name, String lat, String lang, String Category) {
        mView.showProgress();

        if(uri == null){
            mModel.onFinished("Please Select photo first");
            mView.hideProgress();
        }else if(TextUtils.isEmpty(user_id)||TextUtils.isEmpty(show_room_name)||TextUtils.isEmpty(lat)||TextUtils.isEmpty(lang)||TextUtils.isEmpty(Category)){
            mModel.onFinished("Please make sure that you write name and selected location");
            mView.hideProgress();
        }else if(Integer.parseInt(Category)<1){
            mModel.onFinished("Please Select Category first");
            mView.hideProgress();
        }else if(Double.parseDouble(lat)<1 ||Double.parseDouble(lang)<1){
            mModel.onFinished("Please Select Location first");
            mView.hideProgress();
        }else{
            File file = new File( FileUtil.getPath(uri,context));
            final RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse("image/jpg"),
                            file
                    );

            MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);

            WebService.getInstance(false).getApi().addShowRoom(body,Integer.parseInt(user_id),show_room_name,lat,lang,Integer.parseInt(Category)).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: "+t.getLocalizedMessage() );
                    mView.hideProgress();
                }
            });
        }

    }
}
