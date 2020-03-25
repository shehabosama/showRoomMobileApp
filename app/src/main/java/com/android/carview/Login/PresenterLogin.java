package com.android.carview.Login;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;


import com.android.carview.common.helper.AppPreferences;
import com.android.carview.common.helper.Constants;
import com.android.carview.common.model.LoginResponse;
import com.android.carview.common.model.User;
import com.android.carview.common.network.WebService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterLogin implements LoginContract.Presenter  {

    private LoginContract.View mLoginView;
    private LoginContract.Model.onFinishedListener mModel;
    private Context context;
    PresenterLogin(LoginContract.View mLoginView ,LoginContract.Model.onFinishedListener mModel, Context context) {
        this.mLoginView = mLoginView;
        this.mModel  = mModel;
        this.context = context;
    }

    @Override
    public void performLogin(final String email, final String password) {

        mLoginView.showProgress();
        if(TextUtils.isEmpty(email))
        {
            mLoginView.loginValidations();
        }else if (TextUtils.isEmpty(password))
        {
               mLoginView.loginValidations();
        }else if(!isEmailValid(email)){
            mLoginView.emailInvalid();
        }else
        {
            final User user = new User();
            user.email = email;
            user.password = password;

            WebService.getInstance(false).getApi().loginUser(user).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    assert response.body() != null;
                    if (response.body().status == 0) {
                        mLoginView.loginError();

                    } else if (response.body().status == 1) {
                        user.username = response.body().user.username;
                        user.id = Integer.parseInt(response.body().user.id);
                        user.isAdmin = response.body().user.is_admin.equals("1");
                        user.isBlocked = response.body().user.is_bloked.equals("1");

                        user.location = response.body().user.location;
                        if(user.isBlocked){
                            mModel.onFinished("Sorry For this but you Are blocked from Management");
                            mLoginView.hideProgress();
                        }else{
                            mLoginView.setUserInfo(user);
                            mLoginView.loginSuccess();
                            mLoginView.hideProgress();
                            AppPreferences.setString(Constants.AppPreferences.LOGGED_IN_USER_KEY,String.valueOf(user.id),context);
                            if (user.isAdmin) {
                                AppPreferences.setBoolean(Constants.AppPreferences.IS_ADMIN,true,context);
                            } else {
                                AppPreferences.setBoolean(Constants.AppPreferences.IS_ADMIN, false,context);
                            }
                        }


                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    mLoginView.loginError();
                    mLoginView.hideProgress();


                }
            });
        }
    }

    private boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);


        return matcher.matches();
    }





}
