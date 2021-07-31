package com.dragonfly.vanta.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dragonfly.vanta.Model.Repository.RepositoryRequest;
import com.dragonfly.vanta.Model.Repository.RepositoryService;
import com.vantapi.NewRequestMutation;
import com.vantapi.NewServiceMutation;
import com.vantapi.type.CoordinatesInput;
import com.vantapi.type.CoordinatesServInput;
import com.vantapi.type.RequestInput;
import com.vantapi.type.ServiceInput;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ServiceViewModel extends ViewModel {
    private RepositoryService repositoryService;
    private MutableLiveData<NewServiceMutation.Data> serviceCr = new MutableLiveData<>();
    private MutableLiveData<String> toastObserver = new MutableLiveData<>();

    public LiveData<NewServiceMutation.Data> getServiceCr() { return serviceCr; }
    public LiveData<String> getToastObsever() { return toastObserver; }

    public ServiceViewModel() { this.repositoryService = new RepositoryService(); }

    public void newService(CoordinatesServInput coor1, CoordinatesServInput coor2, ServiceInput service){
        CompletableFuture<NewServiceMutation.Data> reqPromise = repositoryService.gqlNewServ(coor1, coor2, service);
        try {
            NewServiceMutation.Data reqData = reqPromise.get(2L, TimeUnit.SECONDS);
            serviceCr.setValue(reqData);
        }catch(Error | ExecutionException | InterruptedException | TimeoutException e){
            toastObserver.setValue("No se pudo comunicar con el servidor");
        }
    }

}
