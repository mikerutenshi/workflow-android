package com.workflow.data.net;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import timber.log.Timber;

/**
 * Created by Michael on 14/06/19.
 */

public class WorkflowResponseInterceptor implements Interceptor {

    public static final String KEY_DATA = "data";
    public static final String KEY_META = "meta";
    public static final String KEY_ERRORS = "errors";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        try {
            Response response = chain.proceed(chain.request());

            final ResponseBody body = response.body();
            JsonParser parser = new JsonParser();

            int responseCode = response.code();

            if (response.isSuccessful()) {
                try {

                    JsonElement jsonElement = parser.parse(body.string());
                    if (!jsonElement.isJsonObject())
                        throw new WorkflowRestException(WorkflowRestException.INVALID_RESPONSE, "Not a Json object");

                    JsonObject bodyJson = jsonElement.getAsJsonObject();

                    if (bodyJson.has(KEY_META)) {
                        final Response.Builder dataMetaResponse = response.newBuilder()
                                .body(ResponseBody.create(JSON, bodyJson.toString()));

                        return dataMetaResponse.build();

                    } else if (bodyJson.has(KEY_DATA)) {
                        JsonElement data = bodyJson.get(KEY_DATA);

                        final Response.Builder dataResponse =
                                response.newBuilder().body(ResponseBody.create(JSON, data.toString()));

                        return dataResponse.build();
                    } else {
                        final Response.Builder dataMetaResponse = response.newBuilder()
                                .body(ResponseBody.create(JSON, bodyJson.toString()));

                        return dataMetaResponse.build();
                    }

                } catch (JsonSyntaxException ex) {
                    Timber.e(ex);
                    throw new WorkflowRestException(WorkflowRestException.INVALID_RESPONSE, ex.getMessage());
                } catch (IllegalStateException stateEx) {
                    Timber.e(stateEx);
                    throw new WorkflowRestException(WorkflowRestException.INVALID_RESPONSE, stateEx.getMessage());
                }
            } else {
                try {
                    // error begin here
                    JsonElement jsonElement = parser.parse(body.string());
                    if (jsonElement.isJsonObject()) {
                        try {
                            // errors
                            JsonObject bodyJson = jsonElement.getAsJsonObject();

                            // json of error must be a json array
                            if (bodyJson.has(KEY_ERRORS) && bodyJson.get(KEY_ERRORS).isJsonArray()) {

                                JsonElement errorsJsonElement = bodyJson.get(KEY_ERRORS).getAsJsonArray();
                                final Response.Builder errorsResponse =
                                        response.newBuilder().body(ResponseBody.create(JSON, errorsJsonElement.toString()));

                                return errorsResponse.build();
                            } else if (bodyJson.has("code")) {

                                if (responseCode >= 500){
                                    Timber.d("Error 500");
                                    throw new WorkflowRestException(WorkflowRestException.BAD_GATEWAY,
                                            "Sedang terjadi kesalahan pada sistem, mohon dicoba kembali...");
                                } else  if (responseCode == 401) {
                                    // it is JWT error
                                    Timber.e(bodyJson.get("message").getAsString());
                                    throw new WorkflowRestException(WorkflowRestException.INVALID_JWT,
                                            bodyJson.get("message").getAsString());
                                } else {
                                    Timber.e(bodyJson.get("message").getAsString());
                                    throw new WorkflowRestException(WorkflowRestException.ERROR_UNKNOWN,
                                            bodyJson.get("message").getAsString());
                                }
                            } else {
                                Timber.e("Errors must be json array");
                                throw new WorkflowRestException(WorkflowRestException.INVALID_RESPONSE,
                                        "Errors must be json array");
                            }
                        } catch (JsonSyntaxException ex) {
                            Timber.e(ex, "Json Syntax Exception");
                            throw new WorkflowRestException(WorkflowRestException.INVALID_RESPONSE, ex.getMessage());
                        }
                    } else {
                        String message = "NOT JSON Object";
                        Timber.e("NOT JSON Object");
                        throw new WorkflowRestException(WorkflowRestException.INVALID_RESPONSE, message);
                    }
                } catch (JsonSyntaxException ex) {
                    Timber.e(ex, "Json Syntax Exception on parse");
                    throw new WorkflowRestException(WorkflowRestException.INVALID_RESPONSE, "Invalid Response from Server");
                }
            }

        } catch (IOException ioEx) {
            Timber.e("IOException: " + ioEx.getLocalizedMessage());
            throw ioEx;
        }
    }
}
