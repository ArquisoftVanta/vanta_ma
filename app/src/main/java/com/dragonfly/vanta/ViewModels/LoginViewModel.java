package com.dragonfly.vanta.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;


import com.dragonfly.vanta.Model.Repository.Repository;
import com.dragonfly.vanta.R;


public class LoginViewModel extends ViewModel {

    private Repository loginRepository;

    LoginViewModel() {
        this.loginRepository = new Repository();
    }


    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        if(isUserNameValid(username) && isPasswordValid(password)){
            loginRepository.gqlLoginUser(username, password);
        }else{
            //code for wrong format
        }


    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}