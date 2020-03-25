package com.android.carview.CarForSellFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.carview.NewCarFragment.PresenterNewCar;
import com.android.carview.R;
import com.android.carview.common.base.BaseFragment;
import com.android.carview.common.model.Car;
import com.android.carview.common.model.CarResponse;

import java.util.ArrayList;
import java.util.List;


public class AllCarForSellFragment extends BaseFragment implements CarForSellContract.Model.onFinishedListener,CarForSellContract.View , AllCarAdapter.AdapterAllCarInterAction {


    PresenterCarForSell presenter;
    PresenterNewCar presenterNewCar;
    private SwipeRefreshLayout srlList;
    private RecyclerView recyclerView;
    List<Car> carList;
    public AllCarForSellFragment() {
        // Required empty public constructor
    }
    public static AllCarForSellFragment newInstance(){
        return new AllCarForSellFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car_for_sell, container, false);
        initializeViews(view);
        setListeners();
        return view;
    }

    @Override
    protected void initializeViews(View v) {
        recyclerView = v.findViewById(R.id.recycler_view);
        srlList = v.findViewById(R.id.srlList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter = new PresenterCarForSell(this,this);

        presenter.performGetAllCar();
        carList = new ArrayList<>();
    }

    @Override
    protected void setListeners() {
        srlList.setOnRefreshListener(srlListRefreshListener);
    }
    private SwipeRefreshLayout.OnRefreshListener srlListRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
                presenter.performGetAllCar();
        }
    };

    @Override
    public void onFinished(String result) {

    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void loadNewCarList(CarResponse carResponse) {
        carList.clear();
        for(int i =0 ;i<carResponse.getCar().size();i++){
            if(Integer.parseInt(carResponse.getCar().get(i).getSell_price())>0&&
                    Integer.parseInt(carResponse.getCar().get(i).getSell_price()) < Integer.parseInt(carResponse.getCar().get(i).getPrice()))
            {
                carList.add(carResponse.getCar().get(i));
            }
        }

        AllCarAdapter newCarAdapter = new AllCarAdapter(carList,getActivity(),this);
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
        presenter.performGetAllCar();
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
    public void onClickItem(final Car car) {
        final CharSequence option[]=new CharSequence[]{"Call him",
                "Delete Car"};

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

