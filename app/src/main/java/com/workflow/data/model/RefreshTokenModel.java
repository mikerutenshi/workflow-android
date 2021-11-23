package com.workflow.data.model;

import com.google.gson.annotations.SerializedName;

public class RefreshTokenModel extends UserNameModel {
    @SerializedName("refresh_token")
    String refreshToken;

    public RefreshTokenModel(String userName, String refreshToken) {
        super(userName);
        this.refreshToken = refreshToken;
    }
}
