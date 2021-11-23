package com.workflow.data.model;

public class WorkQueryModel extends SearchPaginatedQueryModel {

    private Integer workerId;
    private String workerPos;

    public WorkQueryModel(String search, Integer limit, Integer page, Integer workerId, String workerPos) {
        super(search, limit, page);
        this.workerId = workerId;
        this.workerPos = workerPos;
    }

    public WorkQueryModel(String search, Integer limit, Integer page, Integer workerId) {
        super(search, limit, page);
        this.workerId = workerId;
    }

    public WorkQueryModel() {
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public String getWorkerPos() {
        return workerPos;
    }

    public void setWorkerPos(String workerPos) {
        this.workerPos = workerPos;
    }
}
