package com.dragonfly.vanta.Views.Fragments.service;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dragonfly.vanta.R;

public class RequestSelectFragment extends Fragment {

    public RequestSelectFragment() {
        // Required empty public constructor
    }

    public static RequestSelectFragment newInstance() {
        return new RequestSelectFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_select, container, false);
    }
}