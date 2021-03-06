package com.dragonfly.vanta.Views.Fragments.vehicle;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dragonfly.vanta.MainActivity;
import com.dragonfly.vanta.R;
import com.dragonfly.vanta.ViewModels.VehicleViewModel;
import com.vantapi.GetVehicleQuery;
import com.vantapi.GetVehiclesQuery;

import java.util.ArrayList;

public class VehicleFragment extends Fragment {

    public static final String TAG = "logger";

    public static VehicleFragment newInstance() { return new VehicleFragment(); }
    private VehicleViewModel vehicleModel;

    ImageView picture;
    TextView infoMessage, placa, marca, cupo,tServ,linea,tCarro, vSoat, tVeh, modelo, motor, color, gas;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_vehicle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String mail = ((MainActivity) getActivity()).getMail();
        vehicleModel = new ViewModelProvider(requireActivity()).get(VehicleViewModel.class);
        picture = (ImageView) view.findViewById(R.id.vehicleImage);
        infoMessage = (TextView) view.findViewById(R.id.promtedText);
        placa = (TextView) view.findViewById(R.id.data2);
        marca = (TextView) view.findViewById(R.id.data3);
        cupo = (TextView) view.findViewById(R.id.data4);
        tServ = (TextView) view.findViewById(R.id.data5);
        linea = (TextView) view.findViewById(R.id.data6);
        tCarro = (TextView) view.findViewById(R.id.data7);
        vSoat = (TextView) view.findViewById(R.id.data8);
        tVeh = (TextView) view.findViewById(R.id.data9);
        modelo = (TextView) view.findViewById(R.id.data10);
        motor = (TextView) view.findViewById(R.id.data11);
        color = (TextView) view.findViewById(R.id.data12);
        gas = (TextView) view.findViewById(R.id.data13);

        vehicleModel.getVehicle(mail);

        vehicleModel.getVehicleInformation().observe(getActivity(), new Observer<GetVehiclesQuery.GetVehicle>() {
            @Override
            public void onChanged(GetVehiclesQuery.GetVehicle data) {
                Log.d(TAG, data.toString());

                if (data.owner().equals(mail)){

                    infoMessage.setText("Tu vehiculo");
                    picture.setImageResource(R.drawable.car);

                    placa.setText("Placa: " + data.license_plate());
                    marca.setText("Marca: " + data.brand());
                    cupo.setText("No. de pasajeros: " + data.capacity());
                    tServ.setText("Tipo de servicio: " + data.service_type());
                    linea.setText("Linea : " + data.year());
                    tCarro.setText("Tipo de carroceria: " + data.body());
                    vSoat.setText("Fecha vencimiento SOAT: " + data.soat_exp());
                    tVeh.setText("Tipo de vehiculo: " + data.vehicle_type());
                    modelo.setText("Modelo: " + data.model());
                    motor.setText("Cilindraje: " + data.engine());
                    color.setText("Color: " + data.color());
                    gas.setText("Tipo de combustible: " + data.gas_type());
                }
            }
        });
    }
}