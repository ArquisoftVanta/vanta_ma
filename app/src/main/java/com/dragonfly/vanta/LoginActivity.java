package com.dragonfly.vanta;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;



import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.dragonfly.vanta.ViewModels.LoginViewModel;
import com.vantapi.LoginUserMutation;

import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //If there is a JWT Token in the sharedPreferences file, go to App directly
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("jwt", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", "");
        if(!token.isEmpty()){
            openMainApp();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);


        //Button click will send data in TextBoxes to Auth Server
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loginViewModel.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //Receives Text and presents it in a Toast
        loginViewModel.getToastObserver().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        //Receives JWT and goes to next actvity
        loginViewModel.getJWT().observe(this, new Observer<LoginUserMutation.Data>() {
            @Override
            public void onChanged(LoginUserMutation.Data data) {
                saveJwt(data.loginUser().access_token());
                openMainApp();
            }
        });

    }

    //Save Token into shared preferences
    void saveJwt(String jwt){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("jwt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", jwt);
        editor.commit();
    }

    //Start the rest of the App
    void openMainApp(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}