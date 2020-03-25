package com.android.carview.ControlPanelFragment;


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
import com.android.carview.common.helper.Message;
import com.android.carview.common.model.UserList;
import com.android.carview.common.model.UserListResponse;


/**
 * A simple {@link Fragment} subclass.
 */
public class ControlPanelFragment extends BaseFragment implements ControlContract.View ,ControlContract.Model.onFinishedListener,UserAdapter.AdapterOrdersInteractions{
    private PresenterControl presenter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout srlList;
    public ControlPanelFragment() {
        // Required empty public constructor
    }
    public static ControlPanelFragment newInstance (){
        return new ControlPanelFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_control_panel, container, false);
        initializeViews(view);
        setListeners();

        return view;
    }

    @Override
    protected void initializeViews(View v) {
        recyclerView = v.findViewById(R.id.recycler_users);
        srlList = v.findViewById(R.id.srlList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        presenter = new PresenterControl(this,this);
        presenter.performGetAllUser();
    }

    @Override
    protected void setListeners() {
        srlList.setOnRefreshListener(srlListRefreshListener);
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
    public void onFinished(String str) {
        Message.message(getActivity(),str);
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
    public void onFailuer(Throwable t) {
        //Log.e(TAG, "onFailuer: "+t.getLocalizedMessage() );
        Message.message(getActivity(),"Something went wrong");
    }

    @Override
    public void loadUserList(UserListResponse userListResponse) {
        UserAdapter userAdapter = new UserAdapter(getActivity(),userListResponse.getUserLists(),this);
        recyclerView.setAdapter(userAdapter);
        srlList.setRefreshing(false);
    }
    private SwipeRefreshLayout.OnRefreshListener srlListRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            presenter.performGetAllUser();
        }
    };

    @Override
    public void onDeleteUser(UserList userList) {
        presenter.performDeleteUser("delete",Integer.parseInt(userList.getId()));
    }

    @Override
    public void makeAdminPermission(UserList userList) {
       // Message.message(getActivity(),"test");
        presenter.performPermissionUser("permission",Integer.parseInt(userList.getId()),"1");
    }

    @Override
    public void makeNormalUser(UserList userList) {
        presenter.performPermissionUser("permission",Integer.parseInt(userList.getId()),"0");
    }

    @Override
    public void onBlockUser(UserList userList, boolean isChecked) {
        if(isChecked){
            presenter.performBlockUser(Integer.parseInt(userList.getId()),1);
        }else{
            presenter.performBlockUser(Integer.parseInt(userList.getId()),0);


        }
    }
}
