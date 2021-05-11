package com.dragonfly.vanta.Views.Fragments.service;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dragonfly.vanta.MainActivity;
import com.dragonfly.vanta.Model.Repository.RepositoryVehicle;
import com.dragonfly.vanta.R;
import com.dragonfly.vanta.ViewModels.VehicleViewModel;
import com.vantapi.GetVehiclesQuery;

import java.util.ArrayList;
import java.util.List;

public class CarSelectFragment extends DialogFragment {

    private final static String TAG = "VehicleSelect";
    private ListView listViewCar;
    private ArrayList<GetVehiclesQuery.GetVehicle> getVehicleList;
    private ArrayList<String> carNames = new ArrayList<>();
    private VehicleViewModel vehicleViewModel;

    public static CarSelectFragment newInstance() {
        return new CarSelectFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_select, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vehicleViewModel = new ViewModelProvider(requireActivity()).get(VehicleViewModel.class);
        String mail = ((MainActivity) getActivity()).getMail();

        view.findViewById(R.id.listViewCar);

        vehicleViewModel.getVehicle(mail);

        vehicleViewModel.getVehicleInformation().observe(getActivity(), new Observer<GetVehiclesQuery.GetVehicle>() {
            @Override
            public void onChanged(GetVehiclesQuery.GetVehicle getVehicle) {
                getVehicleList = new ArrayList<>();
                getVehicleList.add(getVehicle);
            }
        });

        if(getVehicleList.isEmpty()){
            carNames.add("Usted no tiene ningun carro");
        }else{
            for (GetVehiclesQuery.GetVehicle vehicle : getVehicleList){
                String carString = vehicle.license_plate() + " - " + vehicle.model();
                carNames.add(carString);
            }

            ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, carNames);
            listViewCar.setAdapter(adapter);
            listViewCar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int carId = getVehicleList.get(position).id();
                    NewServiceFragment servFragment = (NewServiceFragment) getActivity().getSupportFragmentManager().findFragmentByTag("Service");
                    servFragment.setCarSelectedId(carId);
                    servFragment.carTxt.setText(carNames.get(position));
                }
            });
        }

    }
}