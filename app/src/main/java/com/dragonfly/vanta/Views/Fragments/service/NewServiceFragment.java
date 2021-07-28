package com.dragonfly.vanta.Views.Fragments.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dragonfly.vanta.MainActivity;
import com.dragonfly.vanta.R;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.List;


public class NewServiceFragment extends Fragment {
    private static final String TAG = "Service";
    int carSelectedId;
    AutocompleteSupportFragment orgFragment, dstFragment;
    TextView orgTxt, dstTxt, carTxt, reqTxt;
    EditText dateText, timeText, valueText;
    Button carSelectButton, reqSelectButton;

    public static NewServiceFragment newInstance() { return new NewServiceFragment(); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_service, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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
    }

    public void setCarSelectedId(int carSelectedId) { this.carSelectedId = carSelectedId; }

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
            }
            @Override
            public void onError(@NonNull Status status) {
            }
        });


    }
}