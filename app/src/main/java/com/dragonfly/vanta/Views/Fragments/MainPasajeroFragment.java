package com.dragonfly.vanta.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dragonfly.vanta.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPasajeroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPasajeroFragment extends Fragment {

    private ImageButton botonNew;
    private ImageButton botonHist;

    public static MainPasajeroFragment newInstance() { return new MainPasajeroFragment(); }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_pasajeros, container, false);
    }
}