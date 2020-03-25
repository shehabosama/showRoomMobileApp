package com.android.carview.SellMyCarFragment;

import android.content.Context;
import android.net.Uri;

import java.util.List;

public interface SellMyCarContract {

    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void onFailuer(Throwable t);
        }
    }
    interface View{
        void showProgress();
        void hideProgress();
    }
    interface Presenter{
        void performPublish(List<Uri> uris, Context context,String userId,String carName,String carDescription,String carModel,String typeFlat ,String price,String sellPrice,String phoneNo);
    }
}
