package com.dragonfly.vanta.ViewModels;

import androidx.lifecycle.ViewModel;

import com.dragonfly.vanta.Model.Repository.RepositoryProfile;

public class ProfileViewModel extends ViewModel {

    private RepositoryProfile profileRepository;

    ProfileViewModel() {
        this.profileRepository = new RepositoryProfile();
    }

    public void getUserProfile(String user_mail){

        profileRepository.gqlGetProfile(user_mail);

    }

}
