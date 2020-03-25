package com.android.carview.EmergencyNumbersFragment;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import com.android.carview.R;
import com.android.carview.common.helper.Message;
import com.android.carview.common.model.EmergenceResponse;

public class Custome_dialogs_emergence implements EmergenceContract.View,EmergenceContract.Model.onFinishedListener{
    EditText editName;
    EditText editNumber;
    Button btnAdd;
    PresenterEmergence presenter;
    Activity activity;


    public void showDialog(final Activity activity){
        this.activity = activity;
        presenter = new PresenterEmergence(this,this);
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       //  dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty);
        // dialog.setCancelable(false);
        dialog.setContentView(R.layout.custome_dialoge);
        dialog.show();
        editName = dialog.findViewById(R.id.txt_name);
        editNumber = dialog.findViewById(R.id.txt_number);
        btnAdd = dialog.findViewById(R.id.Add);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.performAdd(editName.getText().toString(),editNumber.getText().toString());
                dialog.dismiss();

                }
        });



    }

    @Override
    public void onFinished(String result) {
        Message.message(activity,result);
    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void loadEmergenceList(EmergenceResponse response) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
