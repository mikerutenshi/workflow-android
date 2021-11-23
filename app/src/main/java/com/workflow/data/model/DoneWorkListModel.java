package com.workflow.data.model;

import com.google.gson.annotations.SerializedName;

public class DoneWorkListModel extends AssignedWorkListModel {
    @SerializedName("assigned_work_id")
    private Integer assignedWorkId;
    @SerializedName("done_at")
    private String doneAt;
    @SerializedName("quantity_done")
    private Integer quantityDone;

    public Integer getAssignedWorkId() {
        return assignedWorkId;
    }

    public String getDoneAt() {
        return doneAt;
    }

    public Integer getQuantityDone() {
        return quantityDone;
    }

    public void setAssignedWorkId(Integer assignedWorkId) {
        this.assignedWorkId = assignedWorkId;
    }

    public void setDoneAt(String doneAt) {
        this.doneAt = doneAt;
    }

    public void setQuantityDone(Integer quantityDone) {
        this.quantityDone = quantityDone;
    }
}
