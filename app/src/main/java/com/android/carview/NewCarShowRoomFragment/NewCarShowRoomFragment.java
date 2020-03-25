package com.android.carview.NewCarShowRoomFragment;


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
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;

import com.android.carview.R;
import com.android.carview.ShowRoomCarsFragment.ShowRoomCarsFragment;
import com.android.carview.common.base.BaseFragment;
import com.android.carview.common.helper.AppPreferences;
import com.android.carview.common.helper.Constants;
import com.android.carview.common.model.ShowRoom;
import com.android.carview.common.model.ShowRoomResponse;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewCarShowRoomFragment extends BaseFragment implements ShowRoomContract.View,ShowRoomContract.Model.onFinishedListener, ShowRoomAdapter.ShowRoomInterAction {
    private SwipeRefreshLayout srlList;
    private PresenterShowRoom presenter;
    private RecyclerView recyclerView;
    private List<ShowRoom> showRoomList;
    private String bundil;

    public NewCarShowRoomFragment() {
        // Required empty public constructor
    }

    public static NewCarShowRoomFragment newInstance(){
        return new NewCarShowRoomFragment();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_car_show_room, container, false);

        initializeViews(view);
        setListeners();
        return view;
    }

    @Override
    protected void initializeViews(View v) {
        recyclerView = v.findViewById(R.id.recycler_view);
        srlList = v.findViewById(R.id.srlList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter = new PresenterShowRoom(this,this);

        showRoomList = new ArrayList<>();

        bundil = getArguments().getString(Constants.BundleKeys.SHOW_ROM_CATEGORY).toString();
        if(bundil.equals("1")){
            presenter.performGetShowRoom("1");
        }else {
            presenter.performGetShowRoom("2");
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
                presenter.performGetShowRoom("1");
            }else {
                presenter.performGetShowRoom("2");
            }

        }
    };

    @Override
    public void onFinished(String result) {

    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void loadShowRoomList(ShowRoomResponse response) {
        showRoomList.clear();
        showRoomList.addAll(response.getShowRoom());
        ShowRoomAdapter newCarAdapter = new ShowRoomAdapter(getActivity(), showRoomList,this);
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
    public void onClickItem(final ShowRoom showRoom) {

        final CharSequence option[]=new CharSequence[]{"show Products", AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN,getActivity(),false)?"Delete Post":""};

        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        builder.setTitle("Menu");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch(which){
                    case 0:
                        replaceFragment(R.id.container_body, ShowRoomCarsFragment.newInstance(), String.valueOf(Constants.DepartmentConstants.SHOW_CAR_ROOM),Constants.BundleKeys.SHOW_CAR_ROOM, showRoom.getShId());
                        break;
                    case 1:
                        presenter.performDeleteShowRoom(showRoom.getShId());
                        break;
                }

            }
        });
        builder.show();

    }

    @Override
    public void onClickLocation(ShowRoom showRoom) {
        Uri uri = Uri.parse("google.navigation:q="+showRoom.getLat()+","+showRoom.getLang());
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }
}
