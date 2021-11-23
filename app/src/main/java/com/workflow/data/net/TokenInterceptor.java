package com.workflow.data.net;

import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.workflow.utils.PreferenceUtils.PREF_ACCESS_TOKEN;

public class TokenInterceptor implements Interceptor {
    private final SharedPreferences sharedPreferences;

    public TokenInterceptor(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = null;

        if (chain.request().url().encodedPath().contains("/users")
                && chain.request().method().equals("POST")) {
            request = chain.request();
        } else {
            request = chain.request().newBuilder()
                    .addHeader("Authorization",
                            sharedPreferences.getString(PREF_ACCESS_TOKEN, ""))
                    .url(chain.request().url())
                    .build();
        }
        return chain.proceed(request);
    }
}
