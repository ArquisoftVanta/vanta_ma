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

import java.util.List;

import okhttp3.OkHttpClient;

public class RepositoryAuth {

    private ApolloClient apolloClient;
    private OkHttpClient okHttp = new OkHttpClient().newBuilder().build();

    public RepositoryAuth() {
        this.apolloClient = ApolloClient.builder()
            .serverUrl("http://10.0.2.2:8000/graphql/endpoint")
            .okHttpClient(okHttp)
            .build();
    }

    public LoginUserMutation.Data gqlLoginUser(String username, String password){
        final LoginUserMutation.Data[] res = new LoginUserMutation.Data[1];
        this.apolloClient.mutate(new LoginUserMutation(username, password))
                .enqueue(new ApolloCall.Callback<LoginUserMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<LoginUserMutation.Data> response) {
                        if(response.hasErrors()){
                            List<Error> errors = response.getErrors();
                        }else{
                            res[0] = response.getData();
                        }
                    }
                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        System.out.println(e);
                        Log.e("Apollo", "Error", e);
                    }
                });

        return res[0];
    }

    public void gqlRegisterUser(RegisterInput registerInput){
        this.apolloClient.mutate(new RegisterUserMutation(registerInput))
                .enqueue(new ApolloCall.Callback<RegisterUserMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<RegisterUserMutation.Data> response) {
                        System.out.println(response);
                        System.out.println(response.getData());
                        System.out.println(response.getData().registerUser());
                        System.out.println(response.getData().registerUser().__typename());
                        System.out.println(response.getData().registerUser().toString());
                    }
                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        System.out.println(e);
                        Log.e("Apollo", "Error", e);
                    }
                });
    }
}
