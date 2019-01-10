package com.zumper.zumper.network;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/";
    private static final int CONNECTION_TIMEOUT = 30;
    private static final int READ_TIMEOUT = 30;

    private static ZumperApi zumperApi;

    public static ZumperApi getZumperApi() {
        if (zumperApi == null) {
            zumperApi = createZumperApi();
        }
        return zumperApi;
    }

    private static ZumperApi createZumperApi() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        OkHttpClient client = builder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(5, 5, TimeUnit.SECONDS))
                .build();
        return new Retrofit.Builder().baseUrl(BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ZumperApi.class);
    }
}
