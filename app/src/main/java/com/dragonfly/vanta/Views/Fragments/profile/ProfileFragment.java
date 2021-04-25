package com.dragonfly.vanta.Views.Fragments.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dragonfly.vanta.Model.Repository.RepositoryProfile;
import com.dragonfly.vanta.R;
import com.dragonfly.vanta.ViewModels.ProfileViewModel;


public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    private static final String TAG = "LogInternoPerfil";

    public static ProfileFragment newInstance() { return new ProfileFragment(); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Vista perfil iniciada");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

       // profileViewModel.getUserProfile("cdpinedao@unal.edu.co");

        TextView user_mail = (TextView) view.findViewById(R.id.user_mail);
        TextView registry_datetime = (TextView) getView().findViewById(R.id.registry_datetime);
        EditText user_name = (EditText) getView().findViewById(R.id.user_name);
        EditText user_doc = (EditText) getView().findViewById(R.id.user_doc);
        EditText user_phone = (EditText) getView().findViewById(R.id.user_phone);
        EditText user_address = (EditText) getView().findViewById(R.id.user_address);
        EditText rh = (EditText) getView().findViewById(R.id.rh);


        user_mail.setText("cdpinedao@unal.edu.co");
        registry_datetime.setText("2021-04-25 01:21:29");
        user_name.setText("Cesar David Pineda Osorio");
        user_doc.setText("1032494428");
        user_phone.setText("3166226220");
        user_address.setText("Calle 75 # 27-20 Apto 400");
        rh.setText("O+");

        Button button = (Button) view.findViewById(R.id.updateInfo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText rh = (EditText) getView().findViewById(R.id.rh);
                EditText user_doc = (EditText) getView().findViewById(R.id.user_doc);
                String editTextValue = user_doc.getText().toString();
                rh.setText(editTextValue);
            }
        });
        //

        //Log.d(TAG, editTextValue);
        //System.out.println(editTextValue);

        super.onViewCreated(view, savedInstanceState);
    }

}

    /*Button btn = (Button) view.findViewById(R.id.updateInfo);

        btn.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        updateProfile(v);
        }
        });*/