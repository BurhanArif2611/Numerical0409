package com.numerical.numerical.Utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    private static MyApiEndpointInterface loadInterface;
    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.connectTimeout(15, TimeUnit.SECONDS);
            client.readTimeout(15, TimeUnit.SECONDS);
            client.writeTimeout(15, TimeUnit.SECONDS);

            retrofit = new Retrofit.Builder()
                    .baseUrl(Const.BASEURL.URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client.build())
                    .build();
        }
        return retrofit;
    }

    public static MyApiEndpointInterface getLoadInterface() {
        if (loadInterface == null)
            loadInterface = ApiClient.getClient().create(MyApiEndpointInterface.class);
        return loadInterface;
    }
}