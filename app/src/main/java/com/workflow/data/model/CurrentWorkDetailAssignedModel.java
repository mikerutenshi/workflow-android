package com.workflow.data.model;

import com.google.gson.annotations.SerializedName;


public class CurrentWorkDetailAssignedModel {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("position")
    private String position;
    @SerializedName("quantity")
    private Integer quantity;
    @SerializedName("assigned_at")
    private String assignedAt;
    @SerializedName("sum_done")
    private Integer sumDone;
    @SerializedName("quantity_initial")
    private Integer quantityInitial;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getAssignedAt() {
        return assignedAt;
    }

    public Integer getSumDone() {
        return sumDone;
    }

    public Integer getQuantityInitial() {
        return quantityInitial;
    }
}
