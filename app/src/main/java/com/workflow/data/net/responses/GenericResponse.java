package com.workflow.data.net.responses;

import com.google.gson.annotations.SerializedName;
import com.workflow.data.model.ProductModel;

import java.util.List;

/**
 * Created by Michael on 19/06/19.
 */

public class GenericResponse<T> {
    @SerializedName("status")
    String status;
    @SerializedName("data")
    T data;
    @SerializedName("message")
    String message;

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
