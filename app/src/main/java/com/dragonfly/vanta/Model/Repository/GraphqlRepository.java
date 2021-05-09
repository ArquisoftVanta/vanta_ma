package com.dragonfly.vanta.Model.Repository;

import com.apollographql.apollo.ApolloClient;

import okhttp3.OkHttpClient;

public class GraphqlRepository {

    ApolloClient apolloClient;
    OkHttpClient okHttp;

    public GraphqlRepository() {
        this.apolloClient = ApolloClient.builder()
                .serverUrl("http://10.0.2.2:8000/graphql/endpoint")
                .okHttpClient(okHttp)
                .build();

        this.okHttp = new OkHttpClient().newBuilder().build();
    }
}
