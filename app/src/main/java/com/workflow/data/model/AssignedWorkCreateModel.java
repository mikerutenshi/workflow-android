package com.workflow.data.model;

import com.google.gson.annotations.SerializedName;

public class AssignedWorkCreateModel {
    @SerializedName("work_id")
    private Integer workId;
    @SerializedName("worker_id")
    private Integer workerId;
    @SerializedName("position")
    private String position;
    @SerializedName("quantity")
    private Integer quantity;
    @SerializedName("assigned_at")
    private String assignedAt;
    @SerializedName("notes")
    private String notes;

    public AssignedWorkCreateModel(Integer workId, Integer workerId, String position,
                                   Integer quantity, String assignedAt, String notes) {
        this.workId = workId;
        this.workerId = workerId;
        this.position = position;
        this.quantity = quantity;
        this.assignedAt = assignedAt;
        this.notes = notes;
    }
}
