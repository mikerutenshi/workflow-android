package com.workflow.data.net.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Michael on 19/07/19.
 */

public class GenericMetaResponse<T, M> {
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private T data;
    @SerializedName("message")
    private String message;
    @SerializedName("meta")
    private M meta;

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public M getMeta() {
        return meta;
    }
}
