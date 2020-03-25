package com.android.carview.CarSparFragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.carview.R;
import com.android.carview.common.base.BaseFragment;
import com.android.carview.common.helper.AppPreferences;
import com.android.carview.common.helper.Constants;
import com.android.carview.common.helper.Message;
import com.android.carview.common.model.Car;
import com.android.carview.common.model.SparCar;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarSparFragment extends BaseFragment implements CarSparContract.View,CarSparContract.Model.onFinishedListener , SparCarAdapter.SparCarInterAction {

    private SwipeRefreshLayout srlList;
    private RecyclerView recyclerView;
    List<SparCar> sparCars;
    private PresenterCarSpar presenter;
    public CarSparFragment() {
        // Required empty public constructor
    }
    public static CarSparFragment newInstance(){
        return new CarSparFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        initializeViews(view);
        setListeners();
        return view;
    }

    @Override
    protected void initializeViews(View v) {
        recyclerView = v.findViewById(R.id.recycler_view);
        srlList = v.findViewById(R.id.srlList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter = new PresenterCarSpar(this,this);
        sparCars = new ArrayList<>();
        presenter.performGetAllCarSpar();
    }

    @Override
    protected void setListeners() {
        srlList.setOnRefreshListener(srlListRefreshListener);
    }
    private SwipeRefreshLayout.OnRefreshListener srlListRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            presenter.performGetAllCarSpar();
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
    public void loadNewCarSparList(List<SparCar> sparCar) {
        SparCarAdapter sparCarAdapter = new SparCarAdapter(getActivity(),sparCar,this);
        recyclerView.setAdapter(sparCarAdapter);
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
    public void onClickItem(final SparCar sparCar) {
        final CharSequence option[]=new CharSequence[]{ AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN,getActivity(),false)?"Delete":""};

        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        builder.setTitle("Menu");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch(which){
                    case 0:
                       presenter.performDeleteCarSpar(String.valueOf(sparCar.getId()));
                        break;

                }

            }
        });
        builder.show();
        }
}
