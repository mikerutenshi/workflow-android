package com.workflow.data.model;

import com.google.gson.annotations.SerializedName;

public class SignInModel {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    public SignInModel(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
