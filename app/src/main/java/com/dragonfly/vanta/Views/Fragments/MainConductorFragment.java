package com.dragonfly.vanta.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dragonfly.vanta.R;


public class MainConductorFragment extends Fragment {

    private ImageButton botonNew;
    private ImageButton botonHist;

    // TODO: Rename and change types and number of parameters
    public static MainConductorFragment newInstance() { return new MainConductorFragment(); }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_conductor, container, false);
    }
}