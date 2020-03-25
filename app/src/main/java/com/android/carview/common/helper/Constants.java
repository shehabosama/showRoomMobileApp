package com.android.carview.common.helper;

import com.android.carview.R;

public class Constants
{
    public static class BundleKeys{
        public static final String USER_TYPE_KEY="user_type_key";
        public static final String CAR_CATEGORY = "used_cars";
        public static final String NEW_CAR_CATEGORY = "new_cars";
        public static final String SHOW_ROM_CATEGORY ="show_room" ;
        public static final String SHOW_CAR_ROOM ="show_car_room" ;
    }
    public static class AppPreferences {
        public static final int STATUS_LOGGED_OUT = 0;
        public static final int STATUS_WAITING_VERIFICATION_CODE = 1;
        public static final int STATUS_LOGGED_IN = 2;
        public static final String DRIVER_IS_HUB_KEY = "driver_is_hub_key";
        public static final String PUSH_KEY = "push_key";
        public static final String BASE_URL_KEY = "base_url_key";
        public static final String LOGGED_IN_USER_KEY = "logged_in_user_key";
        public static final String ORDERS_LAST_UPDATED_TIME = "orders_last_updated_time_key";
        public static final String OS_ANDROID_KEY = "A";
        public static final String ACCOUNT_NUMBER_KEY = "account_number_key";
        public static final String APPLICATION_NAME_KEY = "application_name_key";
        public static final String TYPE_KEY = "type_key";
        public static final String SESSION_ID_KEY = "session_id_key";
        public static final String LOCATION_KEY = "location_key";
        public static final String IS_ADMIN = "ACCOUNT_IS_ADMIN";

    }
    public class NavigationConstants {
        public static final int ACTION_PROFILE = R.id.action_profile;
        public static final int ACTION_CONTROL = R.id.action_control;
        public static final int ACTION_SETTONGS = R.id.action_settings;
        public static final int ACTION_ABOUTUS = R.id.action_about_us;
        public static final int ACTION_LOGOUT = R.id.action_logout;
        public static final int ACTION_HOME = R.id.action_home;
        public static final int ACTION_CATEGORY = R.id.addCar;
    }
    public class CaregoryResults{
        public static final int CAR_NEW_CATEGORY = 1;
        public static final int CAR_USED_CATEGORY= 2;
    }
    public class DepartmentConstants{

        public static final int CAR_FOR_SELL =10 ;
        public static final int SELL_MY_CAR = 20;
        public static final int USED_CARS =30 ;
        public static final int NEW_CARS = 40;
        public static final int USED_CAR_SHOW_ROOM =50 ;
        public static final int NEW_CAR_SHOW_ROOM = 60;
        public static final int FAVORITE = 70;
        public static final int NOTIFICATION =80 ;
        public static final int EMERGENCY_NUMBER =90 ;
        public static final int ADD_SHOWROOM = 100;
        public static final int SHOW_CAR_ROOM = 15;
        public static final int ADD_SPAR =38 ;
    }
}
