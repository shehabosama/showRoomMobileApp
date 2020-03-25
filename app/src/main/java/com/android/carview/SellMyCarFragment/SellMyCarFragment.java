package com.android.carview.SellMyCarFragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.carview.R;
import com.android.carview.common.base.BaseFragment;
import com.android.carview.common.helper.AppPreferences;
import com.android.carview.common.helper.Constants;
import com.android.carview.common.helper.Message;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellMyCarFragment extends BaseFragment implements SellMyCarContract.View,SellMyCarContract.Model.onFinishedListener{


    private static final int FILE_REQUEST_CODE =101 ;
    private RecyclerView recyclerView;
    private EditText editTextCarName,editTextCarDescription,editTextCarMode,editTextPrice,editTextSellPrice,editTextPhoneNo;
    private Button btnUpload;
    private Spinner spinnerCategory;
    private PresenterSellMyCar presenter;
    private ProgressDialog progressDialog;
    private List<Uri> uri_photo;
    private int SpinnerSelection = 0 ;
    public SellMyCarFragment() {
        // Required empty public constructor
    }
    public static SellMyCarFragment newInstance(){
        return new SellMyCarFragment();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sell_my_car, container, false);
        initializeViews(view);
        setListeners();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.user_menu,menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_add:
                Intent intent = new Intent(getActivity(), FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .setShowImages(true)
                        .enableImageCapture(true)
                        .setMaxSelection(6)
                        .setSkipZeroSizeFiles(true)
                        .build());
                startActivityForResult(intent, FILE_REQUEST_CODE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case FILE_REQUEST_CODE:
                    ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
                    uri_photo = new ArrayList<>();
                    for (int i = 0; i < files.size(); i++) {
                        uri_photo.add(files.get(i).getUri());
                        Log.e("log", "onActivityResult: " + files.get(i).getUri().toString());
                    }
                    PhotoSelectedAdapter photoSelectedAdapter = new PhotoSelectedAdapter(uri_photo, getActivity());
                    recyclerView.setAdapter(photoSelectedAdapter);


                    break;
            }
        }
    }
    @Override
    protected void initializeViews(View v) {
        setHasOptionsMenu(true);
        recyclerView = v.findViewById(R.id.recycler_view);
        editTextCarDescription = v.findViewById(R.id.editTextCarDescription);
        editTextCarName = v.findViewById(R.id.editTextCarName);
        editTextCarMode = v.findViewById(R.id.editTextCarModel);
        btnUpload = v.findViewById(R.id.btn_sell_my_car);
        spinnerCategory = v.findViewById(R.id.spinnerCategory);
        editTextPrice = v.findViewById(R.id.price);
        editTextSellPrice = v.findViewById(R.id.sell_price);
        editTextPhoneNo = v.findViewById(R.id.editTextPhoneNo);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        presenter = new PresenterSellMyCar(this ,this );
        progressDialog = new ProgressDialog(getActivity());

        List<String> categoryList = new ArrayList<>();
        categoryList.add(0,"Select Category");
        categoryList.add(1,"Used");
        categoryList.add(2,"New");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,categoryList);
        spinnerCategory.setAdapter(arrayAdapter);


    }

    @Override
    protected void setListeners() {
        btnUpload.setOnClickListener(btnUploadListener);
        spinnerCategory.setOnItemSelectedListener(spinnerCategoryListener);
    }

    private AdapterView.OnItemSelectedListener spinnerCategoryListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerSelection = position;

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private View.OnClickListener btnUploadListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(TextUtils.isEmpty(editTextCarMode.getText().toString())){
                Message.message(getActivity(),"Please make sure that the model is Number ");
            }else if(uri_photo == null){
                Message.message(getActivity(),"Select Photos first...");
            }else{
                presenter.performPublish(uri_photo,getActivity(),
                                AppPreferences.getString(
                                        Constants.AppPreferences.LOGGED_IN_USER_KEY,
                                        getActivity(),
                                        String.valueOf(0)),
                        editTextCarName.getText().toString(),
                        editTextCarDescription.getText().toString(),
                        editTextCarMode.getText().toString(),
                        String.valueOf(SpinnerSelection),editTextPrice.getText().toString(), TextUtils.isEmpty(editTextSellPrice.getText().toString())?"0.0":editTextSellPrice.getText().toString(),editTextPhoneNo.getText().toString());
            }


        }
    };

    @Override
    public void onFinished(String result) {
        Message.message(getActivity(),result);
    }

    @Override
    public void onFailuer(Throwable t) {
        Message.message(getActivity(),"Something went wrong");
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
}
