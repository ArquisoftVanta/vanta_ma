package com.dragonfly.vanta.Views.Fragments.menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dragonfly.vanta.R;

public class MainPasajeroFragment extends Fragment {

    private ImageButton botonNew;
    private ImageButton botonHist;

    public static MainPasajeroFragment newInstance() { return new MainPasajeroFragment(); }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_pasajeros, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        botonNew = view.findViewById(R.id.imageButtonPNew);
        botonHist = view.findViewById(R.id.imageButtonPHist);

        botonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        botonHist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}