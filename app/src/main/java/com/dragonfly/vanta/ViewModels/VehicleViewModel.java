package com.dragonfly.vanta.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dragonfly.vanta.Model.Repository.RepositoryVehicle;
import com.vantapi.DeleteVehicleMutation;
import com.vantapi.GetVehicleQuery;
import com.vantapi.GetVehiclesQuery;
import com.vantapi.LoginUserMutation;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class VehicleViewModel extends ViewModel {
    private RepositoryVehicle vehicleRepository;
    private MutableLiveData<GetVehiclesQuery.Data> vehiclesInformation = new MutableLiveData<>();
    private MutableLiveData<GetVehiclesQuery.GetVehicle> vehicleInformation = new MutableLiveData<>();
    private MutableLiveData<DeleteVehicleMutation.Data> deletedVehicle = new MutableLiveData<>();

//    public LiveData<GetVehiclesQuery.Data> getVsD(){ return vehiclesInformation; }
//    public LiveData<DeleteVehicleMutation.Data> getDeleted(){ return deletedVehicle; }

    public LiveData<GetVehiclesQuery.Data> getVehiclesInformation() {
        return vehiclesInformation;
    }

    public LiveData<GetVehiclesQuery.GetVehicle> getVehicleInformation() { return vehicleInformation; }

    public LiveData<DeleteVehicleMutation.Data> getDeletedVehicle() {
        return deletedVehicle;
    }

    public VehicleViewModel () { this.vehicleRepository =  new RepositoryVehicle(); }

    public void getVehicles(){
        CompletableFuture<GetVehiclesQuery.Data> vehiclesInfo = vehicleRepository.vehiclesData();
        try {
            GetVehiclesQuery.Data mInformation = vehiclesInfo.get(2L, TimeUnit.SECONDS);
            vehiclesInformation.setValue(mInformation);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void deletedVehicle(){
        CompletableFuture<DeleteVehicleMutation.Data> deletedVehicleInfo = vehicleRepository.deleteV();
        try {
            DeleteVehicleMutation.Data infoDV = deletedVehicleInfo.get(2L, TimeUnit.SECONDS);
            deletedVehicle.setValue(infoDV);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void getVehicle(String mail){
        CompletableFuture<GetVehiclesQuery.GetVehicle> vehicleInfo = vehicleRepository.searchVehicle(mail);
        try {
            GetVehiclesQuery.GetVehicle mInformation = vehicleInfo.get(2L, TimeUnit.SECONDS);
            vehicleInformation.setValue(mInformation);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        } catch (Error e){

        }
    }

}
