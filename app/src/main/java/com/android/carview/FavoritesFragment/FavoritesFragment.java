package com.android.carview.FavoritesFragment;

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

import com.android.carview.NewCarFragment.PresenterNewCar;
import com.android.carview.NewCarFragment.newCarAdapter;
import com.android.carview.R;
import com.android.carview.common.base.BaseFragment;
import com.android.carview.common.helper.AppPreferences;
import com.android.carview.common.helper.Constants;
import com.android.carview.common.helper.Message;
import com.android.carview.common.model.Car;
import com.android.carview.common.model.FavoritesListResponse;
import com.android.carview.common.model.MyFavorite;

import java.util.ArrayList;
import java.util.List;


public class FavoritesFragment extends BaseFragment implements FavoritesContract.Model.onFinishedListener,FavoritesContract.View , FavoriteCarAdapter.AdapterCarInterAction {
    private SwipeRefreshLayout srlList;
    private PresenterFavorites presenter;
    private RecyclerView recyclerView;
    private List<MyFavorite> carList;
    public static FavoritesFragment newInstance(){
        return new FavoritesFragment();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view = inflater.inflate(R.layout.fragment_favorites, container, false);
        initializeViews(view);
        setListeners();
        return view;
    }


    @Override
    protected void initializeViews(View v) {
        recyclerView = v.findViewById(R.id.recycler_view);
        srlList = v.findViewById(R.id.srlList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter = new PresenterFavorites(this,this);
        carList = new ArrayList<>();
        presenter.performGetUsedCar(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"));
    }

    @Override
    protected void setListeners() {

        srlList.setOnRefreshListener(srlListRefreshListener);
    }
    private SwipeRefreshLayout.OnRefreshListener srlListRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {


                presenter.performGetUsedCar(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"));

        }
    };

    @Override
    public void onFinished(String result) {

        Message.message(getActivity(),result);
        onRefrishing();
    }

    @Override
    public void onFailuer(Throwable t) {


    }

    @Override
    public void loadFavoritesCarList(FavoritesListResponse favoritesListResponse) {
        carList.clear();
        carList.addAll(favoritesListResponse.getMyFavorites());
        FavoriteCarAdapter newCarAdapter = new FavoriteCarAdapter(carList,getActivity(),this);
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
    public void onClickUnFavorite(MyFavorite car) {
        presenter.performDeleteItemFavorite(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"),car.getCarId(),"delete");

    }

    @Override
    public void onClickItem(final MyFavorite car) {
        final CharSequence option[]=new CharSequence[]{"Call him"};

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
                }

            }
        });
        builder.show();
    }
}
