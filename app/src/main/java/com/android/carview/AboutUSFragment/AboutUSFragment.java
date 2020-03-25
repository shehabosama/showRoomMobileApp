package com.android.carview.AboutUSFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.carview.R;

public class AboutUSFragment extends Fragment {

    public AboutUSFragment() {
        // Required empty public constructor
    }

    public static AboutUSFragment newInstance() {
        return new AboutUSFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_u, container, false);
    }

}
