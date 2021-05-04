package com.dragonfly.vanta.Views.Fragments.service;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dragonfly.vanta.MainActivity;
import com.dragonfly.vanta.R;
import com.dragonfly.vanta.ViewModels.RequestViewModel;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.vantapi.CreateRequestMutation;
import com.vantapi.NewRequestMutation;
import com.vantapi.type.CoordinatesInput;
import com.vantapi.type.RegisterInput;
import com.vantapi.type.RequestInput;

import java.lang.reflect.Array;
import java.security.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class NewPostFragment extends Fragment {

    private CoordinatesInput coorOrg, coorDst;
    private RequestInput requestInput;
    private String mail;

    EditText dateEditTxt, timeEditTxt;
    TextView orgTextView, dstTextView;
    Button createButton;
    AutocompleteSupportFragment orgFragment, dstFragment;
    private RequestViewModel requestViewModel;

    public static NewPostFragment newInstance() { return new NewPostFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mail = ((MainActivity) getActivity()).getMail();
        return inflater.inflate(R.layout.fragment_new_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // final LocalDate localDate = LocalDate.now();

        requestViewModel = new ViewModelProvider(requireActivity()).get(RequestViewModel.class);

        createButton = view.findViewById(R.id.buttonCreateReq);
        dateEditTxt = view.findViewById(R.id.editTextDate);
        timeEditTxt = view.findViewById(R.id.editTextHora);
        orgTextView = view.findViewById(R.id.textViewDirOrg);
        dstTextView = view.findViewById(R.id.textViewDirDst);

        initializeGooglePlaces();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean notEmptyCoordinates = !coorOrg.address().isEmpty() && !coorDst.address().isEmpty();
                boolean notEmptyInputs = !dateEditTxt.getText().toString().isEmpty() && !timeEditTxt.getText().toString().isEmpty();
                if(notEmptyCoordinates && notEmptyInputs){

                    requestInput = RequestInput.builder()
                            .user_id(mail)
                            .date(dateEditTxt.getText().toString())
                            .time(timeEditTxt.getText().toString())
                            .active("false")
                            .registry_request((dateEditTxt.getText().toString() +"T"+ timeEditTxt.getText().toString()+":00Z")                            )
                            .build();
                    
                    requestViewModel.newRequest(coorOrg, coorDst, requestInput);
                }
            }
        });

        requestViewModel.getRequestCr().observe(getActivity(), new Observer<NewRequestMutation.Data>() {
            @Override
            public void onChanged(NewRequestMutation.Data data) {
                Toast.makeText(getActivity(), "Request Created", Toast.LENGTH_LONG).show();
            }
        });

        requestViewModel.getToastObsever().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) { Toast.makeText(getActivity(), s, Toast.LENGTH_LONG); }
        });
    }

    private void initializeGooglePlaces() {
        List<Place.Field> places = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG);

        if(!Places.isInitialized()){
            Places.initialize(getContext(), "AIzaSyAxm0QLs59dJ34JezS4XmSs75bHKrFUBz0");
        }

        orgFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment_org);
        orgFragment.setPlaceFields(places);
        orgFragment.setCountry("CO");
        orgFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                orgTextView.setText(place.getAddress());
                coorOrg = CoordinatesInput.builder()
                        .address(place.getAddress())
                        .lat(String.valueOf(place.getLatLng().latitude))
                        .lng(String.valueOf(place.getLatLng().longitude))
                        .type("origin")
                        .build();
                System.out.println(place.getAddress() + " - " + place.getLatLng().toString());
            }
            @Override
            public void onError(@NonNull Status status) {
            }
        });


        dstFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment_dst);
        dstFragment.setPlaceFields(places);
        dstFragment.setCountry("CO");
        dstFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                dstTextView.setText(place.getAddress());
                coorDst = CoordinatesInput.builder()
                        .address(place.getAddress())
                        .lat(String.valueOf(place.getLatLng().latitude))
                        .lng(String.valueOf(place.getLatLng().longitude))
                        .type("destination")
                        .build();
                System.out.println(place.getAddress() + " - " + place.getLatLng());
            }
            @Override
            public void onError(@NonNull Status status) {
            }
        });

    }
}