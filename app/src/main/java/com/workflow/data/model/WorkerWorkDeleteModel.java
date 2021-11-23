package com.workflow.data.model;

import java.util.List;

public class WorkerWorkDeleteModel {
    Integer workerId;
    Integer workId;
    String position;

    public WorkerWorkDeleteModel(Integer workerId, Integer workId, String position) {
        this.workerId = workerId;
        this.workId = workId;
        this.position = position;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
