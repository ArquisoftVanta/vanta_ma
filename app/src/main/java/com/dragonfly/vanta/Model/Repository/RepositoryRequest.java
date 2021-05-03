package com.dragonfly.vanta.Model.Repository;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Error;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.vantapi.CreateRequestMutation;
import com.vantapi.LoginUserMutation;
import com.vantapi.NewRequestMutation;
import com.vantapi.type.CoordinatesInput;
import com.vantapi.type.RequestInput;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import okhttp3.OkHttpClient;

public class RepositoryRequest {
    private ApolloClient apolloClient;
    private OkHttpClient okHttp = new OkHttpClient().newBuilder().build();

    public RepositoryRequest() {
        this.apolloClient = ApolloClient.builder()
                .serverUrl("http://10.0.2.2:8000/graphql/endpoint")
                .okHttpClient(okHttp)
                .build();
    }

    public CompletableFuture<CreateRequestMutation.Data> gqlCreateReq(RequestInput requestInput){
        final CompletableFuture <CreateRequestMutation.Data> res = new CompletableFuture<>();
        this.apolloClient.mutate(new CreateRequestMutation(requestInput))
                .enqueue(new ApolloCall.Callback<CreateRequestMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<CreateRequestMutation.Data> response) {
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

    public CompletableFuture<NewRequestMutation.Data> gqlNewReq(CoordinatesInput c1, CoordinatesInput c2, RequestInput req){
        final CompletableFuture<NewRequestMutation.Data> res = new CompletableFuture<>();
        this.apolloClient.mutate(new NewRequestMutation(c1, c2, req))
                .enqueue(new ApolloCall.Callback<NewRequestMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<NewRequestMutation.Data> response) {
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
