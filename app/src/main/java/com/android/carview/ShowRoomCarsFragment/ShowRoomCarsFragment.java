package com.android.carview.ShowRoomCarsFragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.carview.R;
import com.android.carview.common.base.BaseFragment;
import com.android.carview.common.helper.AppPreferences;
import com.android.carview.common.helper.Constants;
import com.android.carview.common.helper.Message;
import com.android.carview.common.model.ShowRoomCarResponse;
import com.android.carview.common.model.ShowRoomCars;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowRoomCarsFragment extends BaseFragment implements ShowRoomCarsContract.Model.onFinishedListener,ShowRoomCarsContract.View, ShowRoomCarAdapter.AdapterCarInterAction {

    private static final int FILE_REQUEST_CODE = 121;
    private SwipeRefreshLayout srlList;
    private PresenterShowRoomCars presenter;
    private RecyclerView recyclerView;
    private List<ShowRoomCars> carList;
    private String bundil;
    private List<Uri> uri_photo;
    private LinearLayout linAdd;
    private RecyclerView recyclerPhotoSelected;
    private EditText carName,carDescription,carModel;
    private Button btnAddPhoto;
    private ProgressDialog progressDialog;
    public ShowRoomCarsFragment() {
        // Required empty public constructor
    }

    public static ShowRoomCarsFragment newInstance(){
        return new ShowRoomCarsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_room_cars, container, false);
        initializeViews(view);
        setListeners();
        return view;
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
                        linAdd.setVisibility(View.VISIBLE);
                        uri_photo.add(files.get(i).getUri());
                        Log.e("log", "onActivityResult: " + files.get(i).getUri().toString());
                    }
                    PhotoSelectedAdapter photoSelectedAdapter = new PhotoSelectedAdapter(uri_photo, getActivity());
                    recyclerPhotoSelected.setAdapter(photoSelectedAdapter);


                    break;
            }
        }
    }

    @Override
    protected void setListeners() {

        srlList.setOnRefreshListener(srlListRefreshListener);
        btnAddPhoto.setOnClickListener(btnAddPhotoListener);
    }
    private View.OnClickListener btnAddPhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("please wait when uploading photo...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            presenter.performAddShowRoomCar(getActivity(),uri_photo,bundil,carName.getText().toString(),carDescription.getText().toString(),carModel.getText().toString());


        }
    };
    private SwipeRefreshLayout.OnRefreshListener srlListRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
                presenter.performGetShowRoomCars(bundil);

        }
    };
    @Override
    protected void initializeViews(View v) {
        if(AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN,getActivity(),false)){
            setHasOptionsMenu(true);
        }else{
            setHasOptionsMenu(false);
        }

        progressDialog = new ProgressDialog(getActivity());
        recyclerView = v.findViewById(R.id.recycler_view);
        srlList = v.findViewById(R.id.srlList);
        srlList.setVisibility(View.VISIBLE);
        linAdd = v.findViewById(R.id.lin_add);
        recyclerPhotoSelected = v.findViewById(R.id.recycler_selected_photo);
        carName = v.findViewById(R.id.edit_car_name);
        carModel = v.findViewById(R.id.edit_car_model);
        carDescription = v.findViewById(R.id.edit_car_description);
        btnAddPhoto = v.findViewById(R.id.btn_add_cars);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerPhotoSelected.setLayoutManager(new GridLayoutManager(getActivity(),3));
        presenter = new PresenterShowRoomCars(this,this);
        carList = new ArrayList<>();
        bundil = getArguments().getString(Constants.BundleKeys.SHOW_CAR_ROOM).toString();
        presenter.performGetShowRoomCars(bundil);


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
      //  getActivity().getMenuInflater().inflate(R.menu.user_menu,menu);
        getActivity().getMenuInflater().inflate(R.menu.user_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_add:
                linAdd.setVisibility(View.VISIBLE);
                srlList.setVisibility(View.GONE);
                Intent intent = new Intent(getActivity(), FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .setShowImages(true)
                        .enableImageCapture(true)
                        .setMaxSelection(6)
                        .setSkipZeroSizeFiles(true)
                        .build());
                startActivityForResult(intent, FILE_REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinished(String result) {
        Message.message(getActivity(),result);

    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void loadShowRoomCarsList(ShowRoomCarResponse carResponse) {
        carList.clear();
        carList.addAll(carResponse.getShowRoomCars());
        ShowRoomCarAdapter ShowRoomCarAdapter = new ShowRoomCarAdapter(carList,getActivity(),this,bundil);
        recyclerView.setAdapter(ShowRoomCarAdapter);
        srlList.setRefreshing(false);
    }

    @Override
    public void onFinishedAddCar(String message) {
        Message.message(getActivity(),message);
        progressDialog.dismiss();
        srlList.setVisibility(View.VISIBLE);
        linAdd.setVisibility(View.GONE);
        srlList.post(new Runnable() {
            @Override public void run() {
                srlList.setRefreshing(true);
                // directly call onRefresh() method
                srlListRefreshListener.onRefresh();
            }
        });
        srlList.setRefreshing(false);

    }

    @Override
    public void showProgress() {
    srlList.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        srlList.setRefreshing(false);
    }

    @Override
    public void onRefrishing() {

    }

    @Override
    public void onClickItem(final ShowRoomCars showRoom) {
        final CharSequence option[]=new CharSequence[]{"delete"};

        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        builder.setTitle("Menu");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch(which){
                    case 0:
                        presenter.performDeleteCar(showRoom.getId());
                        break;
                }

            }
        });
        builder.show();
    }
}
