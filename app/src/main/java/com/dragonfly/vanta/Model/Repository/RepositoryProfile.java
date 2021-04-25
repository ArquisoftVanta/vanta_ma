package com.dragonfly.vanta.Model.Repository;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.vantapi.UpdateUserMutation;
import com.vantapi.UserByIdQuery;

import org.jetbrains.annotations.NotNull;

public class RepositoryProfile {

    private ApolloClient apolloClient;

    public RepositoryProfile(){

        this.apolloClient = ApolloClient.builder()
                .serverUrl("http://10.0.2.2:8000/graphql/endpoint")
                .build();
    }

    public void gqlGetProfile(String username){

        this.apolloClient.query(new UserByIdQuery(username)).enqueue(new ApolloCall.Callback<UserByIdQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<UserByIdQuery.Data> response) {
                Log.d("ResponsePerfil", response.toString());
                Log.d("ResponsePerfil", response.getData().toString());
                Log.d("ResponsePerfil", response.getData().userById().toString());
                Log.d("ResponsePerfil", response.getData().userById().registry_datetime());
                Log.d("ResponsePerfil", response.getData().userById().rh());
                Log.d("ResponsePerfil", response.getData().userById().user_address());
                Log.d("ResponsePerfil", response.getData().userById().user_doc());
                Log.d("ResponsePerfil", response.getData().userById().user_name());
                Log.d("ResponsePerfil", response.getData().userById().user_phone());
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                System.out.println(e);
                Log.e("Apollo", "Error", e);
            }
        });

    }

}
