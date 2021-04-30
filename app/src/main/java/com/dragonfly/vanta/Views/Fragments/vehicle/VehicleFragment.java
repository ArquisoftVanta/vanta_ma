package com.dragonfly.vanta.Views.Fragments.vehicle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.dragonfly.vanta.R;

public class VehicleFragment extends Fragment {

    public static VehicleFragment newInstance() { return new VehicleFragment(); }

    ImageView picture;
    ListView vehicleData;
    ArrayAdapter<String> arrayAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        picture = (ImageView) view.findViewById(R.id.vehicleImage);
        vehicleData = (ListView) view.findViewById(R.id.vehicleInfo);

        picture.setImageResource(R.drawable.car);


    }
}