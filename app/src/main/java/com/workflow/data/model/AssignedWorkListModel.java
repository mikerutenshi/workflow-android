package com.workflow.data.model;

import android.widget.Checkable;

import com.google.gson.annotations.SerializedName;

public class AssignedWorkListModel implements Checkable {
    @SerializedName("id")
    private Integer id;
    @SerializedName("work_id")
    private Integer workId;
    @SerializedName("spk_no")
    private Integer spkNo;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("assigned_at")
    private String assignedAt;
    @SerializedName("article_no")
    private String articleNo;
    @SerializedName("product_category_name")
    private String productCategoryName;
    @SerializedName("position")
    private String position;
    @SerializedName("quantity_initial")
    private Integer quantityInitial;
    @SerializedName("quantity_assigned")
    private Integer quantityAssigned;
    @SerializedName("quantity_remaining")
    private Integer quantityRemaining;
    @SerializedName("notes")
    private String notes;
    @SerializedName("sum_done")
    private Integer sumDone;
    private transient boolean isChecked;

    public Integer getId() {
        return id;
    }

    public Integer getWorkId() {
        return workId;
    }

    public Integer getSpkNo() {
        return spkNo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getAssignedAt() {
        return assignedAt;
    }

    public String getArticleNo() {
        return articleNo;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public String getPosition() {
        return position;
    }

    public Integer getQuantityInitial() {
        return quantityInitial;
    }

    public Integer getQuantityAssigned() {
        return quantityAssigned;
    }

    public Integer getQuantityRemaining() {
        return quantityRemaining;
    }

    public String getNotes() {
        return notes;
    }

    public Integer getSumDone() {
        return sumDone;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public void setSpkNo(Integer spkNo) {
        this.spkNo = spkNo;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setAssignedAt(String assignedAt) {
        this.assignedAt = assignedAt;
    }

    public void setArticleNo(String articleNo) {
        this.articleNo = articleNo;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setQuantityInitial(Integer quantityInitial) {
        this.quantityInitial = quantityInitial;
    }

    public void setQuantityAssigned(Integer quantityAssigned) {
        this.quantityAssigned = quantityAssigned;
    }

    public void setQuantityRemaining(Integer quantityRemaining) {
        this.quantityRemaining = quantityRemaining;
    }

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        isChecked = !isChecked;
    }
}
