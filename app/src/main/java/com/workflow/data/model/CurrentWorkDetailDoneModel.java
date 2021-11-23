package com.workflow.data.model;

import com.google.gson.annotations.SerializedName;


public class CurrentWorkDetailDoneModel {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("position")
    private String position;
    @SerializedName("quantity")
    private Integer quantity;
    @SerializedName("done_at")
    private String doneAt;
    @SerializedName("quantity_remaining")
    private Integer quantityRemaining;

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

    public String getDoneAt() {
        return doneAt;
    }

    public Integer getQuantityRemaining() {
        return quantityRemaining;
    }
}
