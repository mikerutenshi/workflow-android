package com.workflow.data.model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Michael on 09/07/19.
 */

public class ReportSumModel {
    @SerializedName("total_cost")
    Integer totalCost;
    @SerializedName("total_quantity")
    Integer totalQty;

    public Integer getTotalCost() {
        return totalCost;
    }

    public Integer getTotalQty() {
        return totalQty;
    }
}
