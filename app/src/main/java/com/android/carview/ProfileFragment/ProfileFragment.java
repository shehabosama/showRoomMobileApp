package com.android.carview.ProfileFragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.carview.R;
import com.android.carview.common.base.BaseFragment;
import com.android.carview.common.SqlHelper.myDbAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    private TextView textViewName, textViewEmail, textViewAddress;
    private myDbAdapter myDbAdapter;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initializeViews(view);
        setListeners();
        return view;
    }

    @Override
    protected void initializeViews(View v) {
        textViewEmail = v.findViewById(R.id.email);
        textViewName = v.findViewById(R.id.username);
        textViewAddress = v.findViewById(R.id.address);
        myDbAdapter = new myDbAdapter(getActivity());
        textViewAddress.setText(myDbAdapter.getEmployeeName("address"));
        textViewEmail.setText(myDbAdapter.getEmployeeName("email"));
        textViewName.setText(myDbAdapter.getEmployeeName("name"));
    }

    @Override
    protected void setListeners() {

    }
}

