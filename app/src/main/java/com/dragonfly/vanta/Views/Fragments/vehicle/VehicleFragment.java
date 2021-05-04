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

import com.dragonfly.vanta.R;
import com.dragonfly.vanta.ViewModels.VehicleViewModel;
import com.vantapi.GetVehicleQuery;

import java.util.ArrayList;

public class VehicleFragment extends Fragment {

    public static final String TAG = "logger";

    public static VehicleFragment newInstance() { return new VehicleFragment(); }
    private VehicleViewModel vehicleModel;

    ImageView picture;
//    ListView vehicleData;
    TextView infoMessage, placa, marca, cupo,tServ,linea,tCarro, vSoat, tVeh, modelo, motor, color, gas;
//    ArrayList<String> allInfo;

    ArrayAdapter<String> arrayAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_vehicle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vehicleModel = new ViewModelProvider(requireActivity()).get(VehicleViewModel.class);
        picture = (ImageView) view.findViewById(R.id.vehicleImage);
//        vehicleData = (ListView) view.findViewById(R.id.vehicleInfo);
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


        vehicleModel.getVehicle();

        vehicleModel.getVehicleInformation().observe(getActivity(), new Observer<GetVehicleQuery.Data>() {
            @Override
            public void onChanged(GetVehicleQuery.Data data) {
                Log.d(TAG, data.getVehicle().toString());

                if (data.getVehicle().license_plate()!=null){

                    infoMessage.setText("Tu vehiculo");
                    picture.setImageResource(R.drawable.car);

//                    allInfo = new ArrayList<>();
//                    allInfo.add("Placa: " + data.getVehicle().license_plate());
//                    allInfo.add("Marca: " + data.getVehicle().brand());
//                    allInfo.add("No. de pasajeros: " + data.getVehicle().capacity());
//                    allInfo.add("Tipo de servicio: " + data.getVehicle().service_type());
//                    allInfo.add("Linea : " + data.getVehicle().year());
//                    allInfo.add("Tipo de carroceria: " + data.getVehicle().body());
//                    allInfo.add("Fecha vencimiento SOAT: " + data.getVehicle().soat_exp());
//                    allInfo.add("Tipo de vehiculo: " + data.getVehicle().vehicle_type());
//                    allInfo.add("Modelo: " + data.getVehicle().model());
//                    allInfo.add("Cilindraje: " + data.getVehicle().engine());
//                    allInfo.add("Color: " + data.getVehicle().color());
//                    allInfo.add("Tipo de combustible: " + data.getVehicle().gas_type());
                    placa.setText("Placa: " + data.getVehicle().license_plate());
                    marca.setText("Marca: " + data.getVehicle().brand());
                    cupo.setText("No. de pasajeros: " + data.getVehicle().capacity());
                    tServ.setText("Tipo de servicio: " + data.getVehicle().service_type());
                    linea.setText("Linea : " + data.getVehicle().year());
                    tCarro.setText("Tipo de carroceria: " + data.getVehicle().body());
                    vSoat.setText("Fecha vencimiento SOAT: " + data.getVehicle().soat_exp());
                    tVeh.setText("Tipo de vehiculo: " + data.getVehicle().vehicle_type());
                    modelo.setText("Modelo: " + data.getVehicle().model());
                    motor.setText("Cilindraje: " + data.getVehicle().engine());
                    color.setText("Color: " + data.getVehicle().color());
                    gas.setText("Tipo de combustible: " + data.getVehicle().gas_type());

//                    Log.d(TAG, allInfo.toString());

//                    arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, allInfo);
//                    vehicleData.setAdapter(arrayAdapter);
                }

            }
        });
    }
}