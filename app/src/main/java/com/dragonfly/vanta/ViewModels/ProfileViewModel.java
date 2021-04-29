package com.dragonfly.vanta.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dragonfly.vanta.Model.Repository.RepositoryProfile;
import com.vantapi.LoginUserMutation;
import com.vantapi.UserByIdQuery;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ProfileViewModel extends ViewModel {

    private RepositoryProfile profileRepository;
    private MutableLiveData<UserByIdQuery.Data> userUIData = new MutableLiveData();
    //private MutableLiveData<LoginUserMutation.Data> jwt = new MutableLiveData<>();

    public ProfileViewModel() {
        this.profileRepository = new RepositoryProfile();
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

}
