package com.dragonfly.vanta.Model.Repository;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Error;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.vantapi.CreateVehicleMutation;
import com.vantapi.DeleteVehicleMutation;
import com.vantapi.GetVehicleQuery;
import com.vantapi.GetVehiclesQuery;
import com.vantapi.LoginUserMutation;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import okhttp3.OkHttpClient;

public class RepositoryVehicle{

    ApolloClient apolloClient;
    OkHttpClient okHttp = new OkHttpClient().newBuilder().build();
    int id = 1;

    public RepositoryVehicle() {
        this.apolloClient = ApolloClient.builder()
            .serverUrl("http://10.0.2.2:8000/graphql/endpoint")
            .okHttpClient(okHttp)
            .build();
    }

    public CompletableFuture<GetVehiclesQuery.Data> vehiclesData(){
        final CompletableFuture<GetVehiclesQuery.Data> vehiclesInfo = new CompletableFuture<>();
        this.apolloClient.query(new GetVehiclesQuery())
                .enqueue(new ApolloCall.Callback<GetVehiclesQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetVehiclesQuery.Data> response) {
                        if (response.hasErrors()){
                            String errors = "";
                            for (Error e: response.getErrors()) { errors += e.toString(); }
                            vehiclesInfo.completeExceptionally(new ApolloException(errors));
                        }else{
                            vehiclesInfo.complete(response.getData());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        System.out.println(e);
                        Log.e("Apollo", "Error", e);
                        vehiclesInfo.completeExceptionally(e);
                    }
                });
        return vehiclesInfo;
    }

    public CompletableFuture<GetVehicleQuery.Data> vehicleData(){
        final CompletableFuture<GetVehicleQuery.Data> vehicleInfo =  new CompletableFuture<>();
        this.apolloClient.query(new GetVehicleQuery(id))
                .enqueue(new ApolloCall.Callback<GetVehicleQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<GetVehicleQuery.Data> response) {
                        if (response.hasErrors()){
                            String errors = "";
                            for (Error e: response.getErrors()) { errors += e.toString(); }
                            vehicleInfo.completeExceptionally(new ApolloException(errors));
                        }else{
                            vehicleInfo.complete(response.getData());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        System.out.println(e);
                        Log.e("Apollo", "Error", e);
                        vehicleInfo.completeExceptionally(e);
                    }
                });
        return vehicleInfo;
    }

    public CompletableFuture<DeleteVehicleMutation.Data> deleteV(){
        final CompletableFuture<DeleteVehicleMutation.Data> deletedVehicle = new CompletableFuture<>();
        this.apolloClient.mutate(new DeleteVehicleMutation(id))
                .enqueue(new ApolloCall.Callback<DeleteVehicleMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<DeleteVehicleMutation.Data> response) {
                        if (response.hasErrors()){
                            String errors = "";
                            for (Error e: response.getErrors()) { errors += e.toString(); }
                            deletedVehicle.completeExceptionally(new ApolloException(errors));
                        }else{
                            deletedVehicle.complete(response.getData());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        System.out.println(e);
                        Log.e("Apollo", "Error", e);
                        deletedVehicle.completeExceptionally(e);
                    }
                });
        return deletedVehicle;
    }

    public CompletableFuture<GetVehiclesQuery.GetVehicle> searchVehicle(final String mail){
        final CompletableFuture<GetVehiclesQuery.GetVehicle> vehicleInfo = new CompletableFuture<>();
        this.apolloClient.query(new GetVehiclesQuery())
                .enqueue(new ApolloCall.Callback<GetVehiclesQuery.Data>() {
                    boolean haveCar = false;

                    @Override
                    public void onResponse(@NotNull Response<GetVehiclesQuery.Data> response) {
                        if (response.hasErrors()){
                            String errors = "";
                            for (Error e: response.getErrors()) { errors += e.toString(); }
                            vehicleInfo.completeExceptionally(new ApolloException(errors));
                        }else{
                            List<GetVehiclesQuery.GetVehicle> vList = response.getData().getVehicles();

                            for (GetVehiclesQuery.GetVehicle v: vList) {
                                if(v.owner().equals(mail)){
                                    vehicleInfo.complete(v);
                                    haveCar = true;
                                }
                            }
                            if (!haveCar){
                                vehicleInfo.complete(vList.get(0));
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        System.out.println(e);
                        Log.e("Apollo", "Error", e);
                        vehicleInfo.completeExceptionally(e);
                    }
                });
        return  vehicleInfo;
    }

}
