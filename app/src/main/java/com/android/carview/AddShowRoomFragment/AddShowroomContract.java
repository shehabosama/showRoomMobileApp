package com.android.carview.AddShowRoomFragment;

import android.content.Context;
import android.net.Uri;

import com.android.carview.common.model.DepartmentResponse;
import com.android.carview.common.model.UploadResult;

public interface AddShowroomContract
{
    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void onFailuer(Throwable t);
            void loadDepartmentList(DepartmentResponse departmentResponse);
        }
    }
    interface View{
        void showProgress();
        void hideProgress();
        void loadingSuccess();
        void loadingError();


    }
    interface Presenter{

        void performAddShowRoom(Uri uri,Context context,String user_id,String show_room_name,String lat,String lang,String Category);
    }
}
