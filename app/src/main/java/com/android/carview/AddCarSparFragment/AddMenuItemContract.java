package com.android.carview.AddCarSparFragment;

import android.content.Context;
import android.net.Uri;


public interface AddMenuItemContract {

    interface Model {
        interface onFinishedListener {
            void onFinished(String str);
            void onFailuer(Throwable t);

        }
    }
    interface View{
        void showProgress();
        void hideProgress();
        void validation(String str);
    }
    interface Presenter{


        void performAddMenuItem(Context context, Uri uri, String userId,String itemName, String itemDescription, String itemPrice,String location,String phoneNo);
    }
}
