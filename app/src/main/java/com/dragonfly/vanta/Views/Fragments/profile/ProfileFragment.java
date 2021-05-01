package com.dragonfly.vanta.Views.Fragments.profile;

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
import com.dragonfly.vanta.Model.Repository.RepositoryProfile;
import com.dragonfly.vanta.R;
import com.dragonfly.vanta.ViewModels.ProfileViewModel;
import com.vantapi.UserByIdQuery;
import com.vantapi.type.InfoInput;


public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private TextView user_mail;
    private Button button;
    private TextView registry_datetime;
    private EditText user_name;
    private EditText user_doc;
    private EditText user_phone;
    private EditText user_address;
    private EditText rh;
    private String picture;
    private InfoInput toUpdate;

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

        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        final String mail = ((MainActivity) getActivity()).getMail();
        profileViewModel.getUserProfile(mail);

        user_mail = (TextView) view.findViewById(R.id.user_mail);
        registry_datetime = (TextView) view.findViewById(R.id.registry_datetime);
        user_name = (EditText) view.findViewById(R.id.user_name);
        user_doc = (EditText) getView().findViewById(R.id.user_doc);
        user_phone = (EditText) getView().findViewById(R.id.user_phone);
        user_address = (EditText) getView().findViewById(R.id.user_address);
        rh = (EditText) getView().findViewById(R.id.rh);

        user_mail.setText(mail);

        profileViewModel.getUserUIData().observe(getActivity(), new Observer<UserByIdQuery.Data>() {
            @Override
            public void onChanged(UserByIdQuery.Data data) {
                registry_datetime.setText(data.userById().registry_datetime());
                user_name.setText(data.userById().user_name());
                user_doc.setText(data.userById().user_doc());
                user_phone.setText(data.userById().user_phone());
                user_address.setText(data.userById().user_address());
                rh.setText(data.userById().rh());
                picture = data.userById().picture();
            }
        });


        button = (Button) view.findViewById(R.id.updateInfo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toUpdate = InfoInput.builder().rh(rh.getText().toString())
                        .picture(picture)
                        .user_address(user_address.getText().toString())
                        .user_doc(user_doc.getText().toString())
                        .user_mail(user_mail.getText().toString())
                        .user_name(user_name.getText().toString())
                        .user_phone(user_phone.getText().toString()).build();

                profileViewModel.updateUserProfile(mail, toUpdate);

                profileViewModel.getToastObserver().observe(getActivity(), new Observer<String>() {
                    @Override
                    public void onChanged(String message) {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });


        super.onViewCreated(view, savedInstanceState);
    }



}