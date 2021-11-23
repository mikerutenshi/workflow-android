package com.workflow.data.model;

import com.google.gson.annotations.SerializedName;

public class DoneWorkCreateModel {
    @SerializedName("work_id")
    private Integer workId;
    @SerializedName("worker_id")
    private Integer workerId;
    @SerializedName("position")
    private String position;
    @SerializedName("quantity")
    private Integer quantity;
    @SerializedName("done_at")
    private String doneAt;
    @SerializedName("assigned_work_id")
    private Integer assignedWorkId;
    @SerializedName("notes")
    private String notes;

    public DoneWorkCreateModel(Integer workId, Integer workerId, String position, Integer quantity,
                               String doneAt, Integer assignedWorkId, String notes) {
        this.workId = workId;
        this.workerId = workerId;
        this.position = position;
        this.quantity = quantity;
        this.doneAt = doneAt;
        this.assignedWorkId = assignedWorkId;
        this.notes = notes;
    }
}
