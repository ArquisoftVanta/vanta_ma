package com.dragonfly.vanta.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dragonfly.vanta.Model.Repository.RepositoryAuth;
import com.vantapi.NewRequestMutation;
import com.vantapi.RegisterUserMutation;
import com.vantapi.type.RegisterInput;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RegisterViewModel extends ViewModel {

    RepositoryAuth repo;

    public RegisterViewModel() {repo = new RepositoryAuth(); }

    private MutableLiveData<String> toastObserver = new MutableLiveData<>();

    public LiveData<String> getToastObsever() { return toastObserver; }


    public void registerUser(RegisterInput registerInput){
        CompletableFuture<RegisterUserMutation.Data> res = repo.gqlRegisterUser(registerInput);
        try {
            RegisterUserMutation.Data resData = res.get(2L, TimeUnit.SECONDS);
        }catch(Error | ExecutionException | InterruptedException | TimeoutException e){
            toastObserver.setValue("No se pudo comunicar con el servidor");
        }
    }

}
