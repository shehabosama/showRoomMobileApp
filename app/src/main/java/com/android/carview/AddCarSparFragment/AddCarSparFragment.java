package com.android.carview.AddCarSparFragment;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.carview.AddCategory.AddCategoryFragment;
import com.android.carview.R;
import com.android.carview.common.base.BaseFragment;
import com.android.carview.common.helper.AppPreferences;
import com.android.carview.common.helper.Constants;
import com.android.carview.common.helper.Message;
import com.android.carview.common.model.UserListResponse;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCarSparFragment extends BaseFragment implements AddMenuItemContract.View,AddMenuItemContract.Model.onFinishedListener {


    public AddCarSparFragment() {
        // Required empty public constructor
    }

    private static final int CAMERA_REQUEST_CODE = 131;
    private static final int PICK_FROM_GALLERY = 141;
    private Button uploadBtn;
    private ImageView selectImage;
    private EditText itemName, itemDescription, itemPrice,itemLocation,itemPhoneNo;
    private Context context;
    private Uri uri;
    PresenterAddMenuItem presenter;
    ProgressDialog progressDialog;
    String userType;

    public static AddCarSparFragment newInstance() {
        return new AddCarSparFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car_spar, container, false);

        initializeViews(view);
        setListeners();
        return view;
    }

    @Override
    protected void initializeViews(View v) {
        context = getActivity();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
        }

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }


        uploadBtn = v.findViewById(R.id.btn_upload_item);
        selectImage = v.findViewById(R.id.image_select);
        itemName = v.findViewById(R.id.edit_text_item_name);
        itemDescription = v.findViewById(R.id.edit_text_item_description);
        itemPrice = v.findViewById(R.id.edit_text_item_price);
        itemLocation = v.findViewById(R.id.edit_text_location);
        itemPhoneNo = v.findViewById(R.id.edit_text_phone);
        presenter = new PresenterAddMenuItem(this, this);
        progressDialog = new ProgressDialog(getActivity());
    }

    @Override
    protected void setListeners() {
        uploadBtn.setOnClickListener(uploadBtnListener);
        selectImage.setOnClickListener(selectImageListener);
    }

    private View.OnClickListener selectImageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
        }
    };
    private View.OnClickListener uploadBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            presenter.performAddMenuItem(context,
                    uri,
                    AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(), "0"),
                    itemName.getText().toString()
                    ,itemDescription.getText().toString(),
                    itemPrice.getText().toString(),
                    itemLocation.getText().toString(),
                    itemPhoneNo.getText().toString());


        }
    };

    @Override
    public void showProgress() {
        progressDialog.setTitle("Please Wait While Uploading the Menu Item");
        progressDialog.setMessage("login now...");//message which will show  on the dialog box
        progressDialog.setCancelable(false);// not allow the user to cancel the dialog box even done the process
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void validation(String str) {
        Message.message(context, str);
    }

    @Override
    public void onFinished(String str) {
        Log.e(TAG, "onFinished: " + str);
    }

    @Override
    public void onFailuer(Throwable t) {
        Log.e(TAG, "onFinished: " + t.getLocalizedMessage());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                uri = data.getData();
                selectImage.setImageURI(uri);

            }
        }
    }

}