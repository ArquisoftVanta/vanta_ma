package com.dragonfly.vanta.Model.Repository;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Error;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.vantapi.LoginUserMutation;
import com.vantapi.RegisterUserMutation;
import com.vantapi.type.RegisterInput;

import org.jetbrains.annotations.NotNull;
import java.util.concurrent.CompletableFuture;


import okhttp3.OkHttpClient;

public class RepositoryAuth extends GraphqlRepository{

    public RepositoryAuth() {
        super();
    }

    public CompletableFuture<LoginUserMutation.Data> gqlLoginUser(String username, String password){
        final CompletableFuture <LoginUserMutation.Data> res = new CompletableFuture<>();
        this.apolloClient.mutate(new LoginUserMutation(username, password))
                .enqueue(new ApolloCall.Callback<LoginUserMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<LoginUserMutation.Data> response) {
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


    public CompletableFuture<RegisterUserMutation.Data> gqlRegisterUser(RegisterInput registerInput){
        final CompletableFuture<RegisterUserMutation.Data> res = new CompletableFuture<>();
        this.apolloClient.mutate(new RegisterUserMutation(registerInput))
                .enqueue(new ApolloCall.Callback<RegisterUserMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<RegisterUserMutation.Data> response) {
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
