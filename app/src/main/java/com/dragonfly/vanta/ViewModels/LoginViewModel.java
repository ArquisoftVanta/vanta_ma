package com.dragonfly.vanta.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;
import android.widget.Toast;


import com.dragonfly.vanta.Model.Repository.RepositoryAuth;
import com.vantapi.LoginUserMutation;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class LoginViewModel extends ViewModel {

    private RepositoryAuth loginRepository;
    private MutableLiveData<String> toastErrorObserver = new MutableLiveData();
    private MutableLiveData<LoginUserMutation.Data> jwt = new MutableLiveData<>();

    public LiveData<String> getToastObserver(){
        return toastErrorObserver;
    }
    public LiveData<LoginUserMutation.Data> getJWT(){ return jwt; }

    public LoginViewModel() {
        this.loginRepository = new RepositoryAuth();
    }

    //Envio de info de Login al Servidor
    public void login(String username, String password){

        //Para las pruebas un usuario por defecto
        if(username.equals("xxx") && password.equals("xxx")){
            LoginUserMutation.LoginUser loginUser = new LoginUserMutation.LoginUser(
                    "jwt", "xxx"+username+"xxx",
                    "type",9999999, "ni idea");
            jwt.setValue(new LoginUserMutation.Data(loginUser));
        }

        if(isUserNameValid(username) && isPasswordValid(password)){
            CompletableFuture<LoginUserMutation.Data> loginDataPromise = loginRepository.gqlLoginUser(username, password);
            try {
                LoginUserMutation.Data loginData = loginDataPromise.get(2L, TimeUnit.SECONDS);
                jwt.setValue(loginData);
            }catch(Error | ExecutionException | InterruptedException | TimeoutException e){
                toastErrorObserver.setValue("No se pudo comunicar con el servidor");
            }

        }else{
            toastErrorObserver.setValue("Formato de Correo o Contraseña Erroneos");
        }
    }

    // Validacion de Usuario
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

    // Validacion de Contraseña
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 3;
    }
}