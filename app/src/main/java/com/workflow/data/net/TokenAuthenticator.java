package com.workflow.data.net;

import android.content.SharedPreferences;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.workflow.data.model.RefreshTokenModel;
import com.workflow.data.model.RefreshedTokenModel;
import com.workflow.data.net.responses.GenericResponse;
import com.workflow.data.net.services.AuthService;

import java.io.IOException;


import dagger.Lazy;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import timber.log.Timber;

import static com.workflow.utils.PreferenceUtils.PREF_ACCESS_TOKEN;
import static com.workflow.utils.PreferenceUtils.PREF_REFRESH_TOKEN;
import static com.workflow.utils.PreferenceUtils.PREF_USERNAME;

public class TokenAuthenticator implements Authenticator {

    private final Lazy<Retrofit.Builder> retrofitBuilder;
    private final Lazy<SharedPreferences> sharedPreferencesLazy;
    private int retryCounter = 0;

    public TokenAuthenticator(Lazy<Retrofit.Builder> retrofitBuilder, Lazy<SharedPreferences> sharedPreferences) {
        this.retrofitBuilder = retrofitBuilder;
        this.sharedPreferencesLazy = sharedPreferences;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        if (retryCounter > 3) {
            return null;
        }

        if (response.body() != null) {
            JsonParser jsonParser = new JsonParser();

            try {
                JsonElement jsonElement = jsonParser.parse(response.body().string());

                if (jsonElement.isJsonObject()) {
                    try {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();

                        if (jsonObject.has("message")) {

                            if (!jsonObject.get("message").getAsString().contains("jwt expired")) {
                                throw new IOException(jsonObject.get("message").getAsString());
                            }
                        }
                    } catch (JsonSyntaxException ex) {
                        throw new IOException("Json syntax exception when getting object");
                    }
                }
            } catch (JsonSyntaxException ex) {
                throw new IOException("Json syntax exception when parsing");
            }
        }

        synchronized (this) {
            retryCounter ++;
            SharedPreferences sharedPreferences = sharedPreferencesLazy.get();

            String refreshToken = sharedPreferences.getString(PREF_REFRESH_TOKEN, "");
            Timber.d("mRefreshToken: %s", refreshToken);
            String username = sharedPreferences.getString(PREF_USERNAME, "");
            RefreshTokenModel refreshTokenModel = new RefreshTokenModel(username, refreshToken);
            GenericResponse<RefreshedTokenModel> refreshedResponse =
                    retrofitBuilder.get().build().create(AuthService.class).refreshToken(refreshTokenModel).blockingGet();

            if (refreshedResponse != null) {
                RefreshedTokenModel refreshedTokenModel = refreshedResponse.getData();
                String newAccessToken = "Bearer " + refreshedTokenModel.getNewAccessToken();

                sharedPreferences.edit().putString(PREF_ACCESS_TOKEN, newAccessToken).apply();

                return response.request().newBuilder()
                        .header("Authorization", newAccessToken)
                        .build();
            }
        }

        return null;
    }
}
