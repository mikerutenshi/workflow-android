package com.workflow.data.model;

public class WorkerWorkQueryModel extends SearchPaginatedQueryModel {

    Integer workerId;

    public WorkerWorkQueryModel(String search, Integer limit, Integer page, Integer workerId) {
        super(search, limit, page);
        this.workerId = workerId;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }
}
