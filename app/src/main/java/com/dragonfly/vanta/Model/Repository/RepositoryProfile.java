package com.dragonfly.vanta.Model.Repository;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Error;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.vantapi.UpdateUserMutation;
import com.vantapi.UserByIdQuery;
import com.vantapi.type.InfoInput;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class RepositoryProfile extends GraphqlRepository{

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

    public CompletableFuture<UpdateUserMutation.Data> gqlUpdateProfile(String user_mail, InfoInput updateData){

        final CompletableFuture<UpdateUserMutation.Data> userData  = new CompletableFuture<>();
        this.apolloClient.mutate(new UpdateUserMutation(user_mail, updateData)).enqueue(new ApolloCall.Callback<UpdateUserMutation.Data>() {

            @Override
            public void onResponse(@NotNull Response<UpdateUserMutation.Data> response) {

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
