package com.android.carview.NewCarFragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.carview.R;
import com.android.carview.common.base.BaseFragment;
import com.android.carview.common.helper.AppPreferences;
import com.android.carview.common.helper.Constants;
import com.android.carview.common.helper.Message;
import com.android.carview.common.model.Car;
import com.android.carview.common.model.CarResponse;
import com.android.carview.common.model.FavoritesListResponse;
import com.android.carview.common.model.MyFavorite;
import com.android.carview.common.model.UploadResult;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewCarFragment extends BaseFragment implements NewCarContract.View,NewCarContract.Model.onFinishedListener, newCarAdapter.AdapterCarInterAction {

    private SwipeRefreshLayout srlList;
    private PresenterNewCar presenter;
    private RecyclerView recyclerView;
    private List<Car> carList;
    private List<MyFavorite> myFavorites;
    private String bundil;
    public NewCarFragment() {
        // Required empty public constructor
    }
    public static NewCarFragment newInstance(){
        return new NewCarFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_car, container, false);
        initializeViews(view);
        setListeners();

        return view;
    }

    @Override
    protected void initializeViews(View v) {
        recyclerView = v.findViewById(R.id.recycler_view);
        srlList = v.findViewById(R.id.srlList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter = new PresenterNewCar(this,this);

        carList = new ArrayList<>();
        myFavorites = new ArrayList<>();

         bundil = getArguments().getString(Constants.BundleKeys.CAR_CATEGORY).toString();
        if(bundil.equals("1")){
            presenter.performGetUsedCar("1",AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"));
        }else {
            presenter.performGetNewCar("2",AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"));
        }

    }

    @Override
    protected void setListeners() {

        srlList.setOnRefreshListener(srlListRefreshListener);
    }
    private SwipeRefreshLayout.OnRefreshListener srlListRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            if(bundil.equals("1")){
                presenter.performGetUsedCar("1",AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"));
            }else {
                presenter.performGetNewCar("2",AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"));
            }
        }
    };

    @Override
    public void onFinished(String result) {

        Message.message(getActivity(),result);

    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void loadNewCarList(CarResponse carResponse, FavoritesListResponse favoritesListResponse) {
        carList.clear();
        myFavorites.clear();
        carList.addAll(carResponse.getCar());
        myFavorites.addAll(favoritesListResponse.getMyFavorites());
        newCarAdapter newCarAdapter = new newCarAdapter(carList,myFavorites,getActivity(),this);
        recyclerView.setAdapter(newCarAdapter);
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
        if(bundil.equals("1")){
            presenter.performGetUsedCar("1",AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"));
        }else {
            presenter.performGetNewCar("2",AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"));
        }
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
    public void onClickFavorite(Car car) {
        presenter.performAddItemFavorite(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"),car.getCarId());
    }

    @Override
    public void onClickUnFavorite(Car car) {
        presenter.performDeleteItemFavorite(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"),car.getCarId(),"delete");

    }

    @Override
    public void onClickItem(final Car car) {
        final CharSequence option[]=new CharSequence[]{"Call him",AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN,getActivity(),false)?"Delete Post":""};

        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        builder.setTitle("Menu");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch(which){
                    case 0:
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:"+car.getPhone_no()));
                        startActivity(callIntent);
                        break;

                    case 1:
                        presenter.performDeleteCarItem(car.getCarId());
                        break;
                }

            }
        });
        builder.show();
    }
}
