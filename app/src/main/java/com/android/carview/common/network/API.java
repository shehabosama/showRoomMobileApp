package com.android.carview.common.network;

import com.android.carview.common.model.CarResponse;
import com.android.carview.common.model.Department;
import com.android.carview.common.model.DepartmentResponse;
import com.android.carview.common.model.EmergenceResponse;
import com.android.carview.common.model.FavoritesListResponse;
import com.android.carview.common.model.LoginResponse;
import com.android.carview.common.model.MainResponse;
import com.android.carview.common.model.ShowRoomCarResponse;
import com.android.carview.common.model.ShowRoomResponse;
import com.android.carview.common.model.SparCar;
import com.android.carview.common.model.UploadResult;
import com.android.carview.common.model.User;
import com.android.carview.common.model.UserList;
import com.android.carview.common.model.verificationResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API {

    @POST("Login_user.php")
    Call<LoginResponse> loginUser(@Body User user);//to send request and response the message if the account is login or not

    @POST("register_user.php")
    Call<MainResponse> registerUser(@Body User user);// to send request and response the message if the user inserted in database or not
    @FormUrlEncoded
    @POST("index.php")
    Call<verificationResponse> sendVerification(@Field("email") String email);

    @FormUrlEncoded
    @POST("verification.php")
    Call<verificationResponse> reciveVerification(@Field("email") String email, @Field("random") String verification);
    @FormUrlEncoded
    @POST("ResetPassword.php")
    Call<MainResponse> updatPassword(@Field("email") String email,
                                     @Field("password") String password) ;//to send request and response the message if the post update or not

    @Multipart
    @POST("upload_file.php")
    Call <UploadResult>uploadReceipt(
            @Header("Cookie") String sessionIdAndRz,
            @Part MultipartBody.Part file,
            @Part("items") RequestBody items,
            @Part("isAny") RequestBody isAny,@Part("dept_name") String dept_name,@Part("dept_image") String dept_image
    );


    @POST("get-all-department.php")
    Call<List<Department>> getAllDepartment();
    @Multipart
    @POST("upload-car.php")
    Call<MainResponse> uploadImages(@Part List<MultipartBody.Part> images,
                                    @Part("user_id") int user_id,
                                    @Part("car_name") String car_name,
                                    @Part("car_description") String car_description,
                                    @Part("car_model") int car_model,@Part("car_category") int type_flat,@Part("price")Double price,@Part("sell_price")Double sellPrice,@Part("phone_no")String phone_no);
    @FormUrlEncoded
    @POST("add-emergence-number.php")
    Call<MainResponse> uploadEmerNumber(@Field("name")String name,@Field("number") String number);
    @FormUrlEncoded
    @POST("get-all-car.php")
    Call<CarResponse> getCarCategory(@Field("category") int Category);
    @POST("get-all-car.php")
    Call<CarResponse> getAllCar();

    @Multipart
    @POST("Add-show-room.php")
    Call <MainResponse>addShowRoom(
            @Part MultipartBody.Part file,
            @Part("user_id") int user_id,
            @Part("shroom_name") String shroom_name,
            @Part("lat") String lat,
            @Part("lang") String lang,
            @Part("category") int category
    );

    @FormUrlEncoded
    @POST("get-all-show-room.php")
    Call<ShowRoomResponse> getShowRoomCategory(@Field("category") int Category);

    @POST("get-all-emergence-number.php")
    Call<EmergenceResponse> getEmergencies();

    @FormUrlEncoded
    @POST("get-all-show-car.php")
    Call<ShowRoomCarResponse> getShowRoomCars(@Field("sh_id") int sh_id);
    @Multipart
    @POST("add-show-room-car.php")
    Call<MainResponse> addShowRoomCar(@Part List<MultipartBody.Part> images,
                                    @Part("show_room_id") int show_room_id,
                                    @Part("car_name") String car_name,
                                    @Part("car_description") String car_description,
                                    @Part("car_model") int car_model);
    @POST("get-all-user.php")
    Call<List<UserList>> getAllUsers();
    @FormUrlEncoded
    @POST("user_controller.php")
    Call<MainResponse> deleteUser(@Field("delete") String delete,@Field("id") int id);
    @FormUrlEncoded
    @POST("user_controller.php")
    Call<MainResponse> updatePermissionAdmin(@Field("permission_admin") String permission_admin,@Field("id") int id,@Field("value")String value);
    @FormUrlEncoded
    @POST("user_controller.php")
    Call<MainResponse> blockUser(@Field("value")int value,@Field("id") int id);
    @FormUrlEncoded
    @POST("get-user-favorites.php")
    Call<FavoritesListResponse> getAllFavorites(@Field("user_id")int user_id);
    @FormUrlEncoded
    @POST("add-favorite-car.php")
    Call<MainResponse> addFevoriteCar(@Field("user_id")int user_id,@Field("car_id")int car_id);
    @FormUrlEncoded
    @POST("add-favorite-car.php")
    Call<MainResponse> addFevoriteCar(@Field("user_id")int user_id,@Field("car_id")int car_id,@Field("delete")String delete);
    @Multipart
    @POST("add_ads_item.php")
    Call<MainResponse> addSparPartItem(@Header("Cookie") String sessionIdAndRz,
                                       @Part MultipartBody.Part file,
                                       @Part("items") RequestBody items,
                                       @Part("isAny") RequestBody isAny,
                                       @Part("user_id")int user_id,
                                       @Part("item_name") String item_name,
                                       @Part("item_description") String item_description,
                                       @Part("price") String price,
                                       @Part("location")String location);

    @POST("get-all-spar-car.php")
    Call<List<SparCar>> getAllSparCar();

    @FormUrlEncoded
    @POST("update-user-information.php")
    Call<MainResponse> updateUserInformation(@Field("user_id")int user_id,@Field("user_name")String  userName,@Field("user_address")String userAddress);
    @FormUrlEncoded
    @POST("update-user-information.php")
    Call<MainResponse> updateUserInformation(@Field("user_id")int user_id,@Field("user_name")String  userName,@Field("user_address")String userAddress,@Field("password") String password);

    @FormUrlEncoded
    @POST("delete-car-item.php")
    Call<MainResponse> deleteCarItem(@Field("car_id")int car_id);
    @POST("check-user-blocked.php")
    Call<LoginResponse> checkBlocked(@Body User user);
    @FormUrlEncoded
    @POST("delete_show_room_car.php")
    Call<MainResponse> deleteShowRoomCarItem(@Field("car_id")int car_id);
    @FormUrlEncoded
    @POST("delete_show_room.php")
    Call<MainResponse> deleteShowRoomItem(@Field("show_room_id")int car_id);
    @FormUrlEncoded
    @POST("delete_spar.php")
    Call<MainResponse> deleteSpareItem(@Field("spar_id")int car_id);

}
