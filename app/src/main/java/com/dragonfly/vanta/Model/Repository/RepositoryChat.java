package com.dragonfly.vanta.Model.Repository;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Error;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.vantapi.ChatByIdQuery;
import com.vantapi.ChatByUserQuery;
import com.vantapi.RegisterUserMutation;
import com.vantapi.SendMessageMutation;
import com.vantapi.type.RegisterInput;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import okhttp3.OkHttpClient;

public class RepositoryChat{

    ApolloClient apolloClient;
    OkHttpClient okHttp = new OkHttpClient().newBuilder().build();

    public RepositoryChat() {
        this.apolloClient = ApolloClient.builder()
            .serverUrl("http://44.195.120.118:30000/graphql/endpoint")
            .okHttpClient(okHttp)
            .build();
    }

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

    public CompletableFuture<ChatByIdQuery.Data> gqlChatById(String mail, String chatId) {
        final CompletableFuture<ChatByIdQuery.Data> res = new CompletableFuture<>();
        this.apolloClient.query(new ChatByIdQuery(mail, chatId))
                .enqueue(new ApolloCall.Callback<ChatByIdQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<ChatByIdQuery.Data> response) {
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

    public CompletableFuture<SendMessageMutation.Data> gqlSendMessage(String mail, String chatId, String msg) {
        final CompletableFuture<SendMessageMutation.Data> res = new CompletableFuture<>();
        this.apolloClient.mutate(new SendMessageMutation(chatId, mail, msg))
                .enqueue(new ApolloCall.Callback<SendMessageMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<SendMessageMutation.Data> response) {
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
