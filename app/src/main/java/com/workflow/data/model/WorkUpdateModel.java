package com.workflow.data.model;

import com.google.gson.annotations.SerializedName;

public class WorkUpdateModel {
    @SerializedName("spk_no")
    Integer spkNo;
    @SerializedName("product_id")
    Integer productId;
    @SerializedName("product_quantity")
    Integer qty;
    @SerializedName("notes")
    String notes;

    public WorkUpdateModel(Integer spkNo, Integer productId, Integer qty, String notes) {
        this.spkNo = spkNo;
        this.productId = productId;
        this.qty = qty;
        this.notes = notes;
    }
}
