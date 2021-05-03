package com.dragonfly.vanta.Views.Fragments.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dragonfly.vanta.R;
import com.dragonfly.vanta.ViewModels.RegisterViewModel;
import com.vantapi.RegisterUserMutation;
import com.vantapi.type.RegisterInput;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends DialogFragment {

    private final static String TAG = "RegisterDialog";
    private RegisterViewModel registerViewModel;
    EditText editName, editMail, editPass, editPhone, editDoc, editAdress;
    Button buttonSignUp;
    RegisterInput registerInput;

    public static RegisterFragment newInstance() {return new RegisterFragment();}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        editName = view.findViewById(R.id.editTextTextName);
        editMail = view.findViewById(R.id.editTextTextEmail);
        editAdress = view.findViewById(R.id.editTextAddress);
        editDoc = view.findViewById(R.id.editTextDocument);
        editPhone = view.findViewById(R.id.editTextPhone);
        editPass = view.findViewById(R.id.editTextPass);

        buttonSignUp = view.findViewById(R.id.buttonRegister);

        registerViewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = !editName.getText().toString().isEmpty() && !editMail.getText().toString().isEmpty() && !editPhone.getText().toString().isEmpty() &&
                                !editDoc.getText().toString().isEmpty() && !editPass.getText().toString().isEmpty();

                if(valid){
                    registerInput = RegisterInput.builder()
                            .userName(editName.getText().toString())
                            .userMail(editMail.getText().toString())
                            .userDoc(editDoc.getText().toString())
                            .userPhone(editPhone.getText().toString())
                            .password(editPass.getText().toString())
                            .picture("")
                            .rh("")
                            .registryDatetime("")
                            .build();
                    getDialog().dismiss();
                    registerViewModel.registerUser(registerInput);
                }else{
                    Toast.makeText(getActivity(), "Se deben llenar todos los datos requeridos", Toast.LENGTH_LONG);
                }
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}