package com.workflow.data.model;

import com.google.gson.annotations.SerializedName;

public class UserNameModel {
    @SerializedName("username")
    String userName;

    public UserNameModel(String userName) {
        this.userName = userName;
    }
}
