package com.workflow.data.net;

import android.content.SharedPreferences;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.workflow.WorkflowApplication;
import com.workflow.data.model.UserNameModel;
import com.workflow.data.net.responses.GenericResponse;
import com.workflow.data.net.services.AuthService;
import com.workflow.presentation.navigation.Navigator;

import java.io.IOException;

import dagger.Lazy;
import okhttp3.Interceptor;
import okhttp3.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

import static com.workflow.utils.PreferenceUtils.PREF_ACCESS_TOKEN;
import static com.workflow.utils.PreferenceUtils.PREF_WORK_ASSIGNED_FILTER_STATUS;
import static com.workflow.utils.PreferenceUtils.PREF_WORK_DONE_FILTER_STATUS;
import static com.workflow.utils.PreferenceUtils.PREF_NAME;
import static com.workflow.utils.PreferenceUtils.PREF_REFRESH_TOKEN;
import static com.workflow.utils.PreferenceUtils.PREF_ROLE;
import static com.workflow.utils.PreferenceUtils.PREF_USERNAME;
import static com.workflow.utils.PreferenceUtils.PREF_WORKER_FILTER_STATUS;
import static com.workflow.utils.PreferenceUtils.PREF_WORK_ASSIGN_FILTER_STATUS;

public class ErrorInterceptor implements Interceptor {

    private final Lazy<Navigator> navigator;
    private final Lazy<SharedPreferences> sharedPreferencesLazy;
    private final Lazy<Retrofit.Builder> retrofitBuilder;

    public ErrorInterceptor(Lazy<Navigator> navigator, Lazy<SharedPreferences> sharedPreferences, Lazy<Retrofit.Builder> retrofitBuilder) {
        this.navigator = navigator;
        this.sharedPreferencesLazy = sharedPreferences;
        this.retrofitBuilder = retrofitBuilder;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        if (!response.isSuccessful()) {
            if (response.code() == 500) {
                throw new IOException("Terjadi kesalahan pada server");
            } else if (response.code() == 404 && response.message().toLowerCase().contains("not found")) {
                throw new IOException("Server endpoint tidak ditemukan");
            } else if (response.body() != null) {
                JsonParser jsonParser = new JsonParser();

                try {
                    JsonElement jsonElement = jsonParser.parse(response.body().string());

                    if (jsonElement.isJsonObject()) {
                        try {
                            JsonObject jsonObject = jsonElement.getAsJsonObject();

                            if (jsonObject.has("message")) {

                                if (jsonObject.get("message").getAsString().toLowerCase().contains("refresh token is expired")) {
                                    signOut();
                                }
                                throw new IOException(jsonObject.get("message").getAsString());
                            }
                        } catch (JsonSyntaxException ex) {
                            Timber.d("Json syntax exception when getting object");
                            throw new IOException(response.code() + " | " + response.message());
                        }
                    } else {
                        Timber.d("Not a json object");
                        throw new IOException(response.code() + " | " + response.message());
                    }
                } catch (JsonSyntaxException ex) {
                    Timber.d("Json syntax exception when parsing");
                    throw new IOException(response.code() + " | " + response.message());
                }
            }
        }
        return response;
    }

    private void signOut() throws IOException {
        synchronized (this) {
            SharedPreferences sharedPreferences = sharedPreferencesLazy.get();
            UserNameModel userNameModel = new UserNameModel(
                    sharedPreferences.getString(PREF_USERNAME, ""));
            GenericResponse signOutResponse =
                    retrofitBuilder.get().build().create(AuthService.class).signOut(userNameModel).blockingGet();
            if (signOutResponse != null && signOutResponse.getStatus().toLowerCase().equals("ok")) {
                navigator.get().navigateToAuth(WorkflowApplication.getContext());

                sharedPreferences.edit().remove(PREF_ACCESS_TOKEN).apply();
                sharedPreferences.edit().remove(PREF_REFRESH_TOKEN).apply();
                sharedPreferences.edit().remove(PREF_ROLE).apply();
                sharedPreferences.edit().remove(PREF_NAME).apply();
                sharedPreferences.edit().remove(PREF_USERNAME).apply();
                sharedPreferences.edit().remove(PREF_WORKER_FILTER_STATUS).apply();
                sharedPreferences.edit().remove(PREF_WORK_ASSIGN_FILTER_STATUS).apply();
                sharedPreferences.edit().remove(PREF_WORK_DONE_FILTER_STATUS).apply();
                sharedPreferences.edit().remove(PREF_WORK_ASSIGNED_FILTER_STATUS).apply();
            } else {
                throw new IOException("My sign out failed");
            }
        }
    }
}
