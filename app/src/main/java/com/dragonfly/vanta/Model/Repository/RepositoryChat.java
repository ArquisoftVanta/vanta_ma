package com.dragonfly.vanta.Model.Repository;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Error;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.vantapi.ChatByUserQuery;
import com.vantapi.RegisterUserMutation;
import com.vantapi.type.RegisterInput;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import okhttp3.OkHttpClient;

public class RepositoryChat extends GraphqlRepository{

    public RepositoryChat() { super(); }

    public CompletableFuture<ChatByUserQuery.Data> gqlChatByUser(String mail) {
        final CompletableFuture<ChatByUserQuery.Data> res = new CompletableFuture<>();
        this.apolloClient.query(new ChatByUserQuery(mail))
                .enqueue(new ApolloCall.Callback<ChatByUserQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<ChatByUserQuery.Data> response) {
                        if (response.hasErrors()) {
                            String errors = "";
                            for (Error e : response.getErrors()) {
                                errors += e.toString();
                            }
                            res.completeExceptionally(new ApolloException(errors));
                        } else {
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
