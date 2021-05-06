package com.dragonfly.vanta.Views.Fragments.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dragonfly.vanta.R;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.List;

import com.vantapi.type.CoordinatesInput;
import com.vantapi.type.RequestInput;


public class NewServiceFragment extends Fragment {
    AutocompleteSupportFragment orgFragment, dstFragment;



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
        initializeGooglePlaces();
    }
    private void initializeGooglePlaces() {
        List<Place.Field> places = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG);

        if(!Places.isInitialized()){
            Places.initialize(getContext(), "AIzaSyAxm0QLs59dJ34JezS4XmSs75bHKrFUBz0");
        }

        orgFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment_org_ser);
        orgFragment.setPlaceFields(places);
        orgFragment.setCountry("CO");
        orgFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

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

            }
            @Override
            public void onError(@NonNull Status status) {
            }
        });

    }
}