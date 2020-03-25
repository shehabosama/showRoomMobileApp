package com.android.carview.EmergencyNumbersFragment;


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
import com.android.carview.common.helper.Constants;
import com.android.carview.common.model.Emergence;
import com.android.carview.common.model.EmergenceResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmergencyNumbersFragment extends BaseFragment implements EmergenceContract.View,EmergenceContract.Model.onFinishedListener , EmergenceAdapter.EmergenceInterAction {

    private SwipeRefreshLayout srlList;
    private PresenterEmergence presenter;
    private RecyclerView recyclerView;
    private List<Emergence> emergenceList;
    private String bundil;

    public EmergencyNumbersFragment() {
        // Required empty public constructor
    }
    public static EmergencyNumbersFragment newInstance(){
        return new EmergencyNumbersFragment();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emergency_numbers_fragmnet, container, false);

        initializeViews(view);
        setListeners();
        return view;
    }

    @Override
    protected void initializeViews(View v) {
        recyclerView = v.findViewById(R.id.recycler_view);
        srlList = v.findViewById(R.id.srlList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter = new PresenterEmergence(this,this);

        emergenceList = new ArrayList<>();



            presenter.performGetEmergenceNo();

    }

    @Override
    protected void setListeners() {

        srlList.setOnRefreshListener(srlListRefreshListener);
    }
    private SwipeRefreshLayout.OnRefreshListener srlListRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
                presenter.performGetEmergenceNo();

        }
    };

    @Override
    public void onFinished(String result) {

    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void loadEmergenceList(EmergenceResponse response) {
        emergenceList.clear();
        emergenceList.addAll(response.getEmergences());
        EmergenceAdapter newCarAdapter = new EmergenceAdapter(getActivity(),emergenceList,this);
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
    public void onClickItem(Emergence emergence) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+emergence.getPhone()));
        startActivity(callIntent);
    }
}
