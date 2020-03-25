package com.android.carview.AddCategory;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.carview.AddCarSparFragment.AddCarSparFragment;
import com.android.carview.AddShowRoomFragment.AddShowRoomFargment;
import com.android.carview.EmergencyNumbersFragment.Custome_dialogs_emergence;
import com.android.carview.R;
import com.android.carview.SellMyCarFragment.SellMyCarFragment;
import com.android.carview.common.base.BaseFragment;
import com.android.carview.common.helper.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCategoryFragment extends BaseFragment {


    FragmentManager fragmentManager;
    private Button addCar,addShowroom,addEmergenciesNumber,addCarSpar;
    public AddCategoryFragment() {
        // Required empty public constructor
    }

    public static AddCategoryFragment newInstance() {
        return new AddCategoryFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        initializeViews(view);
        setListeners();
        return view;
    }

    @Override
    protected void initializeViews(View v) {
        addCar  = v.findViewById(R.id.btn_add_cars);
        addShowroom = v.findViewById(R.id.btn_showroom);
        addEmergenciesNumber = v.findViewById(R.id.emergence);
        addCarSpar = v.findViewById(R.id.spar);
        fragmentManager =getActivity().getSupportFragmentManager();
    }

    @Override
    protected void setListeners() {

        addShowroom.setOnClickListener(addShowroomListener);
        addCar.setOnClickListener(addCarListener);
        addEmergenciesNumber.setOnClickListener(addEmergenciesNumberListener);
        addCarSpar.setOnClickListener(addCarSparListener);
    }
    private View.OnClickListener addCarSparListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (fragmentManager.findFragmentByTag(String.valueOf(Constants.DepartmentConstants.ADD_SPAR)) != null) {
                Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
                replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.DepartmentConstants.ADD_SPAR))));
            } else {
                replaceFragment(R.id.container_body, AddCarSparFragment.newInstance(), String.valueOf(Constants.DepartmentConstants.ADD_SPAR));
            }
        }
    };
    private View.OnClickListener addEmergenciesNumberListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Custome_dialogs_emergence dialogs_emergence = new Custome_dialogs_emergence();
            dialogs_emergence.showDialog(getActivity());
        }
    };
    private View.OnClickListener addShowroomListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (fragmentManager.findFragmentByTag(String.valueOf(Constants.DepartmentConstants.ADD_SHOWROOM)) != null) {
                Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
                replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.DepartmentConstants.ADD_SHOWROOM))));
            } else {
                replaceFragment(R.id.container_body, AddShowRoomFargment.newInstance(), String.valueOf(Constants.DepartmentConstants.ADD_SHOWROOM));
            }
        }
    };
    private View.OnClickListener addCarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (fragmentManager.findFragmentByTag(String.valueOf(Constants.DepartmentConstants.SELL_MY_CAR)) != null) {
                Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
                replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.DepartmentConstants.SELL_MY_CAR))));
            } else {
                replaceFragment(R.id.container_body, SellMyCarFragment.newInstance(), String.valueOf(Constants.DepartmentConstants.SELL_MY_CAR));
            }
        }
    };
}
