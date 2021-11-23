package com.workflow.data.net.responses;

import com.google.gson.annotations.SerializedName;
import com.workflow.data.model.MetaModel;

public class GenericResponseModel<T> {
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private T data;
    @SerializedName("message")
    private String message;
    @SerializedName("meta")
    private MetaModel meta;

    public GenericResponseModel(String status, T data, String message, MetaModel meta) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.meta = meta;
    }

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public MetaModel getMeta() {
        return meta;
    }
}
