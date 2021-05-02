package com.dragonfly.vanta.Views.Fragments.vehicle;

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
    String intro = "Tu vehiculo";


    ImageView picture;
    ListView vehicleData;
    TextView infoMessage;

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
        vehicleData = (ListView) view.findViewById(R.id.vehicleInfo);
        infoMessage = (TextView) view.findViewById(R.id.promtedText);

        vehicleModel.getVehicle();

        vehicleModel.getVehicleInformation().observe(getActivity(), new Observer<GetVehicleQuery.Data>() {
            @Override
            public void onChanged(GetVehicleQuery.Data data) {

//                 Log.d(TAG, data.toString());
//
//                ArrayList<String> allInfo = new ArrayList<>();
//                allInfo.add("Placa: " + data.getVehicle().get(0).license_plate());
//                allInfo.add("Marca: " + data.getVehicle().get(0).brand());
//                allInfo.add("No. de pasajeros: " + data.getVehicle().get(0).capacity());
//                allInfo.add("TIpo de servicio: " + data.getVehicle().get(0).service_type());
//                allInfo.add("Linea : " + data.getVehicle().get(0).year());
//                allInfo.add("Tipo de carroceria: " + data.getVehicle().get(0).body());
//                allInfo.add("Fecha vencimiento SOAT: " + data.getVehicle().get(0).soat_exp());
//                allInfo.add("Tipo de vehiculo: " + data.getVehicle().get(0).vehicle_type());
//                allInfo.add("Modelo: " + data.getVehicle().get(0).model());
//                allInfo.add("Cilindraje: " + data.getVehicle().get(0).engine());
//                allInfo.add("Color: " + data.getVehicle().get(0).color());
//                allInfo.add("Tipo de combustible: " + data.getVehicle().get(0).gas_type());
//
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, allInfo);
//                vehicleData.setAdapter(arrayAdapter);
//////
                if (!data.equals(null)){

                    infoMessage.setText("Tu vehiculo");

                    ArrayList<String> allInfo = new ArrayList<>();
                    allInfo.add("Placa: " + data.getVehicle().license_plate());
                    allInfo.add("Marca: " + data.getVehicle().brand());
                    allInfo.add("No. de pasajeros: " + data.getVehicle().capacity());
                    allInfo.add("TIpo de servicio: " + data.getVehicle().service_type());
                    allInfo.add("Linea : " + data.getVehicle().year());
                    allInfo.add("Tipo de carroceria: " + data.getVehicle().body());
                    allInfo.add("Fecha vencimiento SOAT: " + data.getVehicle().soat_exp());
                    allInfo.add("Tipo de vehiculo: " + data.getVehicle().vehicle_type());
                    allInfo.add("Modelo: " + data.getVehicle().model());
                    allInfo.add("Cilindraje: " + data.getVehicle().engine());
                    allInfo.add("Color: " + data.getVehicle().color());
                    allInfo.add("Tipo de combustible: " + data.getVehicle().gas_type());

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, allInfo);
                    vehicleData.setAdapter(arrayAdapter);
                }else{
                    infoMessage.setText("No tienes un carro registrado");
                }

            }
        });
    }
}