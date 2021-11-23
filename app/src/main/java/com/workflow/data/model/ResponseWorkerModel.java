package com.workflow.data.model;

import com.google.gson.annotations.SerializedName;

public class ResponseWorkerModel {
    @SerializedName("worker_id")
    Integer id;
    @SerializedName("name")
    String name;
    @SerializedName("position")
    String position;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
