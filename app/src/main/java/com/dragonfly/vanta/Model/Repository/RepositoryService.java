package com.dragonfly.vanta.Model.Repository;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Error;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.vantapi.CreateRequestMutation;
import com.vantapi.NewRequestMutation;
import com.vantapi.NewServiceMutation;
import com.vantapi.type.CoordinatesInput;
import com.vantapi.type.CoordinatesServInput;
import com.vantapi.type.RequestInput;
import com.vantapi.type.ServiceInput;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import okhttp3.OkHttpClient;

public class RepositoryService {
    ApolloClient apolloClient;
    OkHttpClient okHttp = new OkHttpClient().newBuilder().build();

    public RepositoryService() {
        this.apolloClient = ApolloClient.builder()
                .serverUrl("http://44.195.120.118:30000/graphql/endpoint")
                .okHttpClient(okHttp)
                .build();
    }

    public CompletableFuture<NewServiceMutation.Data> gqlNewServ(CoordinatesServInput c1, CoordinatesServInput c2, ServiceInput serv){
        final CompletableFuture<NewServiceMutation.Data> res = new CompletableFuture<>();
        this.apolloClient.mutate(new NewServiceMutation(c1, c2, serv))
                .enqueue(new ApolloCall.Callback<NewServiceMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<NewServiceMutation.Data> response) {
                        if(response.hasErrors()){
                            String errors = "";
                            for (Error e: response.getErrors()) { errors += e.toString(); }
                            res.completeExceptionally(new ApolloException(errors));
                        }else{
                            res.complete(response.getData());
                        }
                    }
                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        System.out.println(e);
                        Log.e("Apollo", "Error", e);
                        res.completeExceptionally(e);
                    }
                });

        return res;
    }
}
