package com.workflow.data.model;

import com.google.gson.annotations.SerializedName;


public class RefreshedTokenModel {
    @SerializedName("access_token")
    private String newAccessToken;

    public String getNewAccessToken() {
        return newAccessToken;
    }
}
