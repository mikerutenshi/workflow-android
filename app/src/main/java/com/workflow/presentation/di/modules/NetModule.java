package com.workflow.presentation.di.modules;

import android.content.SharedPreferences;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.workflow.BuildConfig;
import com.workflow.data.net.ErrorInterceptor;
import com.workflow.data.net.TokenAuthenticator;
import com.workflow.data.net.TokenInterceptor;
import com.workflow.presentation.navigation.Navigator;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module()
public class NetModule {
    @Provides
    Retrofit apiRetrofit(OkHttpClient httpClient, Gson gson) {
        return new Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)
            .build();
    }

    @Provides
    OkHttpClient httpClient(HttpLoggingInterceptor loggingInterceptor,
                            TokenInterceptor tokenInterceptor,
                            TokenAuthenticator tokenAuthenticator,
                            ErrorInterceptor errorInterceptor) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.connectTimeout(10, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(10, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(10, TimeUnit.SECONDS);

        httpClientBuilder.authenticator(tokenAuthenticator);
        httpClientBuilder.addInterceptor(errorInterceptor);
        httpClientBuilder.addInterceptor(tokenInterceptor);

        if (BuildConfig.DEBUG) {
            if (!httpClientBuilder.interceptors().contains(loggingInterceptor))
                httpClientBuilder.addInterceptor(loggingInterceptor);
        }

        return httpClientBuilder.build();
    }

    @Provides
    HttpLoggingInterceptor loggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    TokenInterceptor tokenInterceptor(SharedPreferences sharedPreferences) {
        return new TokenInterceptor(sharedPreferences);
    }

    @Provides
    Retrofit.Builder tokenRetrofit(ErrorInterceptor errorInterceptor) {
        //retrofit exclusively for token refresh
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(errorInterceptor);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            if (!httpClientBuilder.interceptors().contains(loggingInterceptor))
                httpClientBuilder.addInterceptor(loggingInterceptor);
        }

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build());
    }

    @Provides
    ErrorInterceptor errorInterceptor(Lazy<Navigator> navigator, Lazy<Retrofit.Builder> retrofitBuilder,
                                          Lazy<SharedPreferences> sharedPreferences) {
        return new ErrorInterceptor(navigator, sharedPreferences, retrofitBuilder);
    }

    @Provides
    TokenAuthenticator tokenAuthenticator(Lazy<Retrofit.Builder> retrofitBuilder,
                                          Lazy<SharedPreferences> sharedPreferences) {
        return new TokenAuthenticator(retrofitBuilder, sharedPreferences);
    }
}
