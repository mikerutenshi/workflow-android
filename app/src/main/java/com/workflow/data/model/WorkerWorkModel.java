package com.workflow.data.model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Michael on 08/07/19.
 */

public class WorkerWorkModel implements MultiChoiceable {
    @SerializedName("worker_id")
    private Integer workerId;
    @SerializedName("work_id")
    private Integer workId;
    @SerializedName("position")
    private String position;
    @SerializedName("spk_no")
    private String spkNo;
    @SerializedName("article_no")
    private String articleNo;
    @SerializedName("product_quantity")
    private Integer qty;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("done_at")
    private String doneAt;
    private transient boolean isSelected;

    public WorkerWorkModel() {
    }

    public WorkerWorkModel(Integer workerId, Integer workId, String position, String createdAt) {
        this.workerId = workerId;
        this.workId = workId;
        this.position = position;
        this.createdAt = createdAt;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public Integer getWorkId() {
        return workId;
    }

    public String getPosition() {
        return position;
    }

    public String getSpkNo() {
        return spkNo;
    }

    public String getArticleNo() {
        return articleNo;
    }

    public Integer getQty() {
        return qty;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDoneAt() {
        return doneAt;
    }

    @Override
    public void toggleIsSelected() {
        isSelected = !isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
