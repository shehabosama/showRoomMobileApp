package com.android.carview.Login;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.carview.MainActivity.MainActivity;
import com.android.carview.R;
import com.android.carview.Register.RegisterActivity;
import com.android.carview.common.SqlHelper.myDbAdapter;
import com.android.carview.common.base.BaseActivity;
import com.android.carview.common.helper.Message;
import com.android.carview.common.model.User;

public class LoginActivity extends BaseActivity implements LoginContract.View,LoginContract.Model.onFinishedListener{

    private String toast;
    private Button btnLogin;
    private EditText editTextEmail,editTextPassword;
    private TextView textViewRegister;
    private ProgressDialog progressDialog;
    private myDbAdapter helper;
    private PresenterLogin presenter;
    public static void startActivity(Context context){
        context.startActivity(new Intent(context, LoginActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();
        setListeners();
    }

    @Override
    protected void initializeViews() {
        btnLogin = findViewById(R.id.btn_login);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewRegister = findViewById(R.id.textViewRegister);
        progressDialog = new ProgressDialog(this);
        helper = new myDbAdapter(this);
        presenter = new PresenterLogin(this,this,this);
    }
    @Override
    public void showProgress() {
        progressDialog.setTitle("wait minuet..");//title which will show  on the dialog box
        progressDialog.setMessage("login now...");//message which will show  on the dialog box
        progressDialog.setCancelable(false);// not allow the user to cancel the dialog box even done the process
        progressDialog.show();// turn on the dialog box
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void loginValidations() {
        Message.message(LoginActivity.this,"pleas fill all the flied");
    }

    @Override
    public void loginSuccess() {
        Message.message(LoginActivity.this,"login successfully");
        hideProgress();
        startActivity(new Intent(getApplicationContext(), MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @Override
    public void loginError() {
        hideProgress();
        Message.message(LoginActivity.this,"Login Failure");
    }

    @Override
    public void emailInvalid() {
        hideProgress();
        Message.message(LoginActivity.this,"Please Make Sure From Your Email");
    }

    @Override
    public void setUserInfo(User user) {
        helper.insertData(user.username,editTextPassword.getText().toString(),editTextEmail.getText().toString(),String.valueOf(user.id),user.location);
    }


    @Override
    protected void setListeners() {
        btnLogin.setOnClickListener(btnLoginListener);
        textViewRegister.setOnClickListener(textViewRegisterListener);
    }
    View.OnClickListener btnLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.performLogin(editTextEmail.getText().toString(),editTextPassword.getText().toString());
            // MainActivity.startActivity(LoginActivity.this);
        }
    };
    View.OnClickListener textViewRegisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RegisterActivity.startActivity(LoginActivity.this);
        }
    };
  



    @Override
    public void onFinished(String user) {
        Message.message(LoginActivity.this,user);
    }

    @Override
    public void onFailuer(Throwable t) {

    }
}

