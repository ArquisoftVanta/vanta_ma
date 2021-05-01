package com.dragonfly.vanta.Views.Fragments.service;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dragonfly.vanta.R;
import com.dragonfly.vanta.ViewModels.RequestViewModel;
import com.vantapi.CreateRequestMutation;

public class NewPostFragment extends Fragment {

    EditText adrOrgEditTxt, adrDestEditTxt, dateEditTxt, timeEditTxt;
    Button createButton;
    private RequestViewModel requestViewModel;

    public static NewPostFragment newInstance() { return new NewPostFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestViewModel = new ViewModelProvider(requireActivity()).get(RequestViewModel.class);

        createButton = view.findViewById(R.id.buttonCreateReq);
        adrDestEditTxt  = view.findViewById(R.id.editTextOrigen);
        adrOrgEditTxt = view.findViewById(R.id.editTextDestino);
        dateEditTxt = view.findViewById(R.id.editTextDate);
        timeEditTxt = view.findViewById(R.id.editTextHora);


        requestViewModel.getRequestCr().observe(getActivity(), new Observer<CreateRequestMutation.Data>() {
            @Override
            public void onChanged(CreateRequestMutation.Data data) {

            }
        });

        requestViewModel.getToastObsever().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) { Toast.makeText(getActivity(), s, Toast.LENGTH_LONG); }
        });
    }
}