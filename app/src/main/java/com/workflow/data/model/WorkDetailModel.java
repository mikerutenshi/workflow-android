package com.workflow.data.model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Michael on 25/07/19.
 */

public class WorkDetailModel {
    @SerializedName("spk_no")
    private Integer spkNo;
    @SerializedName("article_no")
    private String articleNo;
    @SerializedName("position")
    private String position;
    @SerializedName("unit_cost")
    private Integer unitCost;
    @SerializedName("product_quantity")
    private Integer qty;
    @SerializedName("worker_subtotal_cost")
    private Integer totalCost;
    @SerializedName("created_at")
    private String createdAt;

    public WorkDetailModel() {
    }

    public WorkDetailModel(Integer spkNo, String articleNo, String position, Integer unitCost, Integer qty, Integer totalCost) {
        this.spkNo = spkNo;
        this.articleNo = articleNo;
        this.position = position;
        this.unitCost = unitCost;
        this.qty = qty;
        this.totalCost = totalCost;
    }

    public Integer getSpkNo() {
        return spkNo;
    }

    public String getArticleNo() {
        return articleNo;
    }

    public Integer getUnitCost() {
        return unitCost;
    }

    public Integer getQty() {
        return qty;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public String getPosition() {
        return position;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
