package com.dragonfly.vanta.Views.Fragments.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dragonfly.vanta.MainActivity;
import com.dragonfly.vanta.R;

import com.dragonfly.vanta.ViewModels.RequestViewModel;
import com.dragonfly.vanta.ViewModels.ServiceViewModel;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.vantapi.NewServiceMutation;
import com.vantapi.type.CoordinatesServInput;
import com.vantapi.type.RequestInput;
import com.vantapi.type.ServiceInput;

import java.util.Arrays;
import java.util.List;


public class NewServiceFragment extends Fragment {
    private static final String TAG = "Service";

    private ServiceInput serviceInput;
    private CoordinatesServInput coorOrg, coorDst;
    private ServiceViewModel serviceViewModel;

    int carSelectedId;
    AutocompleteSupportFragment orgFragment, dstFragment;
    TextView orgTxt, dstTxt, carTxt, reqTxt;
    EditText dateText, timeText, valueText;
    Button carSelectButton, reqSelectButton, servSaveButton;
    private String mail;


    public static NewServiceFragment newInstance() { return new NewServiceFragment(); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mail = ((MainActivity) getActivity()).getMail();
        return inflater.inflate(R.layout.fragment_new_service, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        serviceViewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);

        super.onViewCreated(view, savedInstanceState);
        orgTxt = view.findViewById(R.id.textViewOrg);
        dstTxt = view.findViewById(R.id.textViewDst);
        dateText = view.findViewById(R.id.fecha);
        timeText = view.findViewById(R.id.hora);
        valueText = view.findViewById(R.id.valor);
        carTxt = view.findViewById(R.id.carTextView);
        reqTxt = view.findViewById(R.id.reqTextView);
        carSelectButton = view.findViewById(R.id.escogerVehiculo);
        reqSelectButton = view.findViewById(R.id.escogerPasajeros);
        servSaveButton = view.findViewById(R.id.crearServicio);

        initializeGooglePlaces();

        carSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CarSelectFragment carSelect = new CarSelectFragment();
                //carSelect.show(getParentFragmentManager(), "VehicleSelect");
            }
        });

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getDateDialog(dateText);
            }
        });

        servSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean notEmptyCoordinates = !coorOrg.address().isEmpty() && !coorDst.address().isEmpty();
                boolean notEmptyInputs = !dateText.getText().toString().isEmpty() && !timeText.getText().toString().isEmpty();
                boolean notValueInput = !valueText.getText().toString().isEmpty();
                if(notEmptyCoordinates && notEmptyInputs && notValueInput) {
                    serviceInput = ServiceInput.builder()
                            .user_id(mail)
                            .date(dateText.getText().toString())
                            .time(timeText.getText().toString())
                            .service_value(Integer.valueOf(valueText.getText().toString()))
                            .state_service(true)
                            .places(2)
                            .vehicle_id("1")
                            .build();

                    serviceViewModel.newService(coorOrg, coorDst, serviceInput);
                }else {
                    Toast.makeText(getActivity(), "No se llenaron todos lo campos requeridos", Toast.LENGTH_LONG).show();
                }

            }
        });

        //Receives Text and presents it in a Toast
        serviceViewModel.getToastObsever().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            }
        });

        serviceViewModel.getServiceCr().observe(getActivity(), new Observer<NewServiceMutation.Data>() {
            @Override
            public void onChanged(NewServiceMutation.Data data) {
                Toast.makeText(getActivity(), "Servicio Creado", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void setCarSelectedId(int carSelectedId) { this.carSelectedId = carSelectedId; }

    //Google places configuration
    private void initializeGooglePlaces() {
        List<Place.Field> places = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG);

        if(!Places.isInitialized()){
            Places.initialize(getContext(),  getString(R.string.google_maps_api_key));
        }

        orgFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment_org_ser);
        orgFragment.setPlaceFields(places);
        orgFragment.setCountry("CO");
        orgFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                orgTxt.setText(place.getAddress());
                coorOrg = CoordinatesServInput.builder()
                        .service_id(-1)
                        .address(place.getAddress())
                        .lat(String.valueOf(place.getLatLng().latitude))
                        .lng(String.valueOf(place.getLatLng().longitude))
                        .typeC("origin")
                        .orderC(0)
                        .build();
            }
            @Override
            public void onError(@NonNull Status status) {
            }
        });

        dstFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment_dst_ser);
        dstFragment.setPlaceFields(places);
        dstFragment.setCountry("CO");
        dstFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                dstTxt.setText(place.getAddress());
                coorDst = CoordinatesServInput.builder()
                        .service_id(-1)
                        .address(place.getAddress())
                        .lat(String.valueOf(place.getLatLng().latitude))
                        .lng(String.valueOf(place.getLatLng().longitude))
                        .typeC("destination")
                        .orderC(-1)
                        .build();
            }
            @Override
            public void onError(@NonNull Status status) {
            }
        });


    }
}