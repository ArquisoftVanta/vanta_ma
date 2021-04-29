package com.dragonfly.vanta.Model.Repository;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Error;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.vantapi.UpdateUserMutation;
import com.vantapi.UserByIdQuery;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class RepositoryProfile {

    private ApolloClient apolloClient;

    public RepositoryProfile(){

        this.apolloClient = ApolloClient.builder()
                .serverUrl("http://192.168.43.3:8000/graphql/endpoint")
                .build();
    }

    public CompletableFuture<UserByIdQuery.Data> gqlGetProfile(String username){

        final CompletableFuture<UserByIdQuery.Data> userData  = new CompletableFuture<>();
        this.apolloClient.query(new UserByIdQuery(username)).enqueue(new ApolloCall.Callback<UserByIdQuery.Data>() {

            @Override
            public void onResponse(@NotNull Response<UserByIdQuery.Data> response) {

                if (response.hasErrors()){
                    String errors = "";
                    for (Error e: response.getErrors()) { errors += e.toString(); }
                    userData.completeExceptionally(new ApolloException(errors));
                }else{
                    userData.complete(response.getData());
                }

            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                System.out.println(e);
                Log.e("Apollo", "Error", e);
                userData.completeExceptionally(e);
            }

        });
        return userData;
    }

}
