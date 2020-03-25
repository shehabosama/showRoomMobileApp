package com.android.carview.Register;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.android.carview.MainActivity.MainActivity;
import com.android.carview.R;
import com.android.carview.common.base.BaseActivity;
import com.android.carview.common.helper.Message;

public class RegisterActivity extends BaseActivity implements RegisterContract.View  {
    private PresenterRegister presenter;
    private ProgressDialog progressDialog;
    private EditText editTExtusername,editTextEmail,editTextPassword,editTextConfirmPassword,editTextPhoneNumber;
    private Button btnRegister;

    public static void startActivity (Context context){
        context.startActivity(new Intent(context, RegisterActivity.class));
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigster);
        initializeViews();
        setListeners();
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
        hideProgress();
        Message.message(RegisterActivity.this,"password is mismatch");

    }

    @Override
    public void loginSuccess() {
        Message.message(RegisterActivity.this,"Registered successfully");
        hideProgress();
        MainActivity.startActivity(RegisterActivity.this);
        finish();
    }

    @Override
    public void loginError() {
        hideProgress();
        Message.message(RegisterActivity.this,"Login Failure");
    }

    @Override
    public void emailInvalid() {
        hideProgress();
        Message.message(RegisterActivity.this,"Please Make Sure From Your Email");
    }

    @Override
    public void setUserName(String username) {

    }

    @Override
    protected void initializeViews() {
        editTExtusername = findViewById(R.id.edit_text_name);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        btnRegister = findViewById(R.id.btn_register);
        progressDialog = new ProgressDialog(this);
        presenter = new PresenterRegister(this,this);
    }

    @Override
    protected void setListeners() {
        btnRegister.setOnClickListener(btnRegisterListener);
    }
    private View.OnClickListener btnRegisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.performRegister(editTExtusername.getText().toString(),editTextEmail.getText().toString(),editTextPassword.getText().toString(),editTextConfirmPassword.getText().toString(),"test","0");
        }
    };
}
