package com.android.carview.HomeFragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.carview.CarForSellFragment.AllCarForSellFragment;
import com.android.carview.EmergencyNumbersFragment.EmergencyNumbersFragment;
import com.android.carview.FavoritesFragment.FavoritesFragment;
import com.android.carview.Login.LoginActivity;
import com.android.carview.NewCarFragment.NewCarFragment;
import com.android.carview.NewCarShowRoomFragment.NewCarShowRoomFragment;
import com.android.carview.CarSparFragment.CarSparFragment;
import com.android.carview.R;
import com.android.carview.SellMyCarFragment.SellMyCarFragment;
import com.android.carview.common.SqlHelper.myDbAdapter;
import com.android.carview.common.base.BaseFragment;
import com.android.carview.common.helper.AppPreferences;
import com.android.carview.common.helper.Constants;
import com.android.carview.common.model.Department;
import com.android.carview.common.model.LoginResponse;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements DepartmentAdapter.AdapterDepartmentInterAction,MainContract.Model.onFinishedListener,MainContract.View{
    private static final int CAMERA_REQUEST_CODE = 131;
    private static final int PICK_FROM_GALLERY = 141;
    private boolean isAdmin;
//    private SwipeRefreshLayout srlList;
//    private Uri uri;
    private PresenterMainActivity presenter;
    private RecyclerView recyclerDepartment;
    private List<Department> departmentList;
    private myDbAdapter helper;
    private Context context;
    private EditText editText;
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //setHasOptionsMenu(true);
        initializeViews(view);
        setListeners();
        return view;
    }

//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_FROM_GALLERY) {
//            if (resultCode == Activity.RESULT_OK) {
//                uri = data.getData();
//
//            }
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }

//    @Override
//    public void onFinished(UploadResult uploadResult) {
//        Message.message(getActivity(),uploadResult.message);
//        srlList.post(new Runnable() {
//            @Override public void run() {
//                srlList.setRefreshing(true);
//                // directly call onRefresh() method
//                srlListRefreshListener.onRefresh();
//            }
//        });
//        srlList.setRefreshing(false);
//    }
//
//    @Override
//    public void onFailuer(Throwable t) {
//        Message.message(getActivity(),t.getLocalizedMessage());
//
//    }
//
//    @Override
//    public void loadDepartmentList(DepartmentResponse departmentResponse) {
//        departmentList.addAll(departmentResponse.getDepartmentList());

//        srlList.setRefreshing(false);
//    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if(isAdmin){
          getActivity().getMenuInflater().inflate(R.menu.user_menu, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
//            case R.id.action_publish:
////                if(uri !=null){
////                    if(!TextUtils.isEmpty(editText.getText().toString())) {
////                        presenter.performRequest(getActivity(), uri,editText.getText().toString());
////                    }else{
////                        Message.message(getActivity(),"Please enter the department Name");
////
////                    }
////
////                }else{
////                    Message.message(getActivity(),"Please Select the photo first");
////                }
//                break;
            case R.id.action_add:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initializeViews(View v) {
        context = getActivity();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
        } else if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
        helper = new myDbAdapter(context);
        isAdmin = AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN,getActivity(),false);
        presenter = new PresenterMainActivity(this,this);
       // editText = ((Activity)context).findViewById(R.id.editTextToolbar);
      //  editText.setVisibility(View.VISIBLE);
        //srlList = v.findViewById(R.id.srlList);


      //  presenter = new PresenterMain(this);
        departmentList = new ArrayList<>();

        departmentList.add(new Department("Cars For Sell",R.drawable.ic_directions_car_black_24dp));
        departmentList.add(new Department("Sell My Car",R.drawable.car_sales));
        departmentList.add(new Department("Used Cars",R.drawable.used_car));
        departmentList.add(new Department("New Cars",R.drawable.new_car));
        departmentList.add(new Department("Used Car ShowRoom",R.drawable.used_showroom));
        departmentList.add(new Department("New Car ShowRoom",R.drawable.new_showroom));
        departmentList.add(new Department("Favorites",R.drawable.favorite));
        departmentList.add(new Department("Spare Part",R.drawable.spare));
        departmentList.add(new Department("Emergencies Number",R.drawable.ic_call_black_24dp));




        recyclerDepartment = v.findViewById(R.id.recycler_department);
        recyclerDepartment.setLayoutManager(new GridLayoutManager(getActivity(), 3));
       // presenter.performGetDepartment();
        DepartmentAdapter departmentAdapter = new DepartmentAdapter(getActivity(),departmentList,this);
        recyclerDepartment.setAdapter(departmentAdapter);
        presenter.performCheckIsBlocked(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),""));

    }

    @Override
    protected void setListeners() {
       // srlList.setOnRefreshListener(srlListRefreshListener);
    }
    private SwipeRefreshLayout.OnRefreshListener srlListRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            departmentList.clear();
           // presenter.performGetDepartment();
        }
    };


    @Override
    public void onClickDepartment(int position, String deptName) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        switch(position){
            case 0:

                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.DepartmentConstants.CAR_FOR_SELL)) != null) {
                    Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
                    replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.DepartmentConstants.CAR_FOR_SELL))));
                } else {
                    replaceFragment(R.id.container_body, AllCarForSellFragment.newInstance(), String.valueOf(Constants.DepartmentConstants.CAR_FOR_SELL));
                }
                break;
            case 1:

                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.DepartmentConstants.SELL_MY_CAR)) != null) {
                    Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
                    replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.DepartmentConstants.SELL_MY_CAR))));
                } else {
                    replaceFragment(R.id.container_body, SellMyCarFragment.newInstance(), String.valueOf(Constants.DepartmentConstants.SELL_MY_CAR));
                }
                break;
            case 2:

                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.DepartmentConstants.USED_CARS)) != null) {
                    Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
                    replaceFragment(R.id.container_body, NewCarFragment.newInstance(), String.valueOf(Constants.DepartmentConstants.USED_CARS),Constants.BundleKeys.CAR_CATEGORY,String.valueOf(1));
                } else {
                    replaceFragment(R.id.container_body, NewCarFragment.newInstance(), String.valueOf(Constants.DepartmentConstants.USED_CARS),Constants.BundleKeys.CAR_CATEGORY,String.valueOf(1));
                }
                break;
            case 3:

                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.DepartmentConstants.NEW_CARS)) != null) {
                    Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
                    replaceFragment(R.id.container_body, NewCarFragment.newInstance(), String.valueOf(Constants.DepartmentConstants.NEW_CARS),Constants.BundleKeys.CAR_CATEGORY,String.valueOf(2));
                } else {
                    replaceFragment(R.id.container_body, NewCarFragment.newInstance(), String.valueOf(Constants.DepartmentConstants.NEW_CARS),Constants.BundleKeys.CAR_CATEGORY,String.valueOf(2));
                }

                break;
            case 4:


                    replaceFragment(R.id.container_body, NewCarShowRoomFragment.newInstance(), String.valueOf(Constants.DepartmentConstants.USED_CAR_SHOW_ROOM),Constants.BundleKeys.SHOW_ROM_CATEGORY,String.valueOf(1));


                break;
            case 5:

                    replaceFragment(R.id.container_body, NewCarShowRoomFragment.newInstance(), String.valueOf(Constants.DepartmentConstants.USED_CAR_SHOW_ROOM),Constants.BundleKeys.SHOW_ROM_CATEGORY,String.valueOf(2));


                break;
            case 6:

                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.DepartmentConstants.FAVORITE)) != null) {
                    Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
                    replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.DepartmentConstants.FAVORITE))));
                } else {
                    replaceFragment(R.id.container_body, FavoritesFragment.newInstance(), String.valueOf(Constants.DepartmentConstants.FAVORITE));
                }

                break;
            case 7:

                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.DepartmentConstants.NOTIFICATION)) != null) {
                    Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
                    replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.DepartmentConstants.NOTIFICATION))));
                } else {
                    replaceFragment(R.id.container_body, CarSparFragment.newInstance(), String.valueOf(Constants.DepartmentConstants.NOTIFICATION));
                }

                break;
            case 8:

                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.DepartmentConstants.EMERGENCY_NUMBER)) != null) {
                    Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
                    replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.DepartmentConstants.EMERGENCY_NUMBER))));
                } else {
                    replaceFragment(R.id.container_body,  EmergencyNumbersFragment.newInstance(), String.valueOf(Constants.DepartmentConstants.EMERGENCY_NUMBER));
                }

                break;
            case 9:

//                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_HOME)) != null) {
//                    Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
//                    replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_HOME))));
//                } else {
//                    replaceFragment(R.id.container_body, HomeFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_HOME));
//                }

                break;
        }
    }

    @Override
    public void onFinished(LoginResponse loginResponse) {
        if (loginResponse.user.is_bloked.equals("1")){
            String data = helper.getEmployeeName("name");
            helper.delete(data);
            LoginActivity.startActivity(getActivity());
        }
    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
