package com.dragonfly.vanta.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dragonfly.vanta.Model.Repository.RepositoryProfile;
import com.vantapi.LoginUserMutation;
import com.vantapi.UpdateUserMutation;
import com.vantapi.UserByIdQuery;
import com.vantapi.type.InfoInput;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ProfileViewModel extends ViewModel {

    private RepositoryProfile profileRepository;
    private MutableLiveData<UserByIdQuery.Data> userUIData = new MutableLiveData();
    private MutableLiveData<String> toastDataChanged = new MutableLiveData();

    public ProfileViewModel() {
        this.profileRepository = new RepositoryProfile();
    }
    public LiveData<String> getToastObserver(){
        return toastDataChanged;
    }

    public LiveData<UserByIdQuery.Data> getUserUIData() {
        return userUIData;
    }

    public void getUserProfile(String user_mail){

        CompletableFuture<UserByIdQuery.Data> userData = profileRepository.gqlGetProfile(user_mail);

        try {
            UserByIdQuery.Data userDecoded = userData.get(2L, TimeUnit.SECONDS);
            userUIData.setValue(userDecoded);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

    public void updateUserProfile(String user_mail, InfoInput update_info){

        CompletableFuture<UpdateUserMutation.Data> userData = profileRepository.gqlUpdateProfile(user_mail, update_info);

        try {
            UpdateUserMutation.Data userDecoded = userData.get(2L, TimeUnit.SECONDS);
            toastDataChanged.setValue("Datos Actualizados Exitosamente");
        } catch (InterruptedException e) {
            e.printStackTrace();
            toastDataChanged.setValue("Error al Actualizar Datos (Interrumped)");
        } catch (ExecutionException e) {
            e.printStackTrace();
            toastDataChanged.setValue("Error al Actualizar Datos (Execution)");
        } catch (TimeoutException e) {
            e.printStackTrace();
            toastDataChanged.setValue("Error al Actualizar Datos (Timeout)");
        }

    }

}
