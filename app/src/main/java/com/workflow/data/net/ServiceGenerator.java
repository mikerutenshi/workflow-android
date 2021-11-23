package com.workflow.data.net;

import com.workflow.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Michael on 12/06/19.
 */

public class ServiceGenerator {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    // private static OkHttpClient.Builder httpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build());

    public static Retrofit retrofit = builder.build();

    public static <T> T createService(Class<T> serviceClass) {

        // httpClient.addInterceptor(new SimpleResponseInterceptor());

        if (BuildConfig.DEBUG) {
            if (!httpClient.interceptors().contains(logging)) {
                httpClient.addInterceptor(logging);
                builder.client(httpClient.build());
            }
        } else {
            builder.client(httpClient.build());
        }

        retrofit = builder.build();

        return retrofit.create(serviceClass);
    }
}
