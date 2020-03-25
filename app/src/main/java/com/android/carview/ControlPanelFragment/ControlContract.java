package com.android.carview.ControlPanelFragment;

import com.android.carview.common.model.UserListResponse;

public interface ControlContract {
    interface Model {
        interface onFinishedListener {
            void onFinished(String str);
            void onFailuer(Throwable t);
            void loadUserList(UserListResponse userListResponse);
        }
    }
    interface View{
        void showProgress();
        void hideProgress();



    }
    interface Presenter{

        void performGetAllUser();
        void performDeleteUser(String delete, int id);
        void performPermissionUser(String permission, int id, String value);
        void performBlockUser(int id, int value);
    }
}
