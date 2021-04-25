package com.dragonfly.vanta.Model.Repository;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.vantapi.LoginUserMutation;

import org.jetbrains.annotations.NotNull;

public class Repository {

    private ApolloClient apolloClient;

    public Repository() {
        this.apolloClient = ApolloClient.builder()
            .serverUrl("http://10.0.2.2:8000/graphql/endpoint")
            .build();
    }

    public void gqlLoginUser(String username, String password){
        this.apolloClient.mutate(new LoginUserMutation(username, password))
                .enqueue(new ApolloCall.Callback<LoginUserMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<LoginUserMutation.Data> response) {
                        System.out.println(response);
                        System.out.println(response.getData());
                        System.out.println(response.getData().loginUser());
                        System.out.println(response.getData().loginUser().__typename());
                        System.out.println(response.getData().loginUser().toString());
                    }
                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        System.out.println(e);
                        Log.e("Apollo", "Error", e);
                    }
                });
    }
}
