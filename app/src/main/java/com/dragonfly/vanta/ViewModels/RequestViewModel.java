package com.dragonfly.vanta.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dragonfly.vanta.Model.Repository.RepositoryRequest;
import com.vantapi.CreateRequestMutation;
import com.vantapi.type.RequestInput;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RequestViewModel extends ViewModel {
    private RepositoryRequest repositoryRequest;
    private MutableLiveData<CreateRequestMutation.Data> requestCr = new MutableLiveData<>();
    private MutableLiveData<String> toastObserver = new MutableLiveData<>();

    public LiveData<CreateRequestMutation.Data> getRequestCr() { return requestCr; }
    public LiveData<String> getToastObsever() { return toastObserver; }

    public RequestViewModel() { this.repositoryRequest = new RepositoryRequest(); }

    public void createRequest(RequestInput request){
        CompletableFuture<CreateRequestMutation.Data> reqPromise = repositoryRequest.gqlCreateReq(request);
        try {
            CreateRequestMutation.Data reqData = reqPromise.get(2L, TimeUnit.SECONDS);
            requestCr.setValue(reqData);
        }catch(Error | ExecutionException | InterruptedException | TimeoutException e){
            toastObserver.setValue("No se pudo comunicar con el servidor");
        }
    }
}
