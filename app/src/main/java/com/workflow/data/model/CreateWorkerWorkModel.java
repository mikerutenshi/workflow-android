package com.workflow.data.model;

import java.util.List;

public class CreateWorkerWorkModel {
    private String workerPos;
    private List<WorkerWorkModel> workerWorkModels;

    public CreateWorkerWorkModel(String workerPos, List<WorkerWorkModel> workerWorkModels) {
        this.workerPos = workerPos;
        this.workerWorkModels = workerWorkModels;
    }

    public String getWorkerPos() {
        return workerPos;
    }

    public void setWorkerPos(String workerPos) {
        this.workerPos = workerPos;
    }

    public List<WorkerWorkModel> getWorkerWorkModels() {
        return workerWorkModels;
    }

    public void setWorkerWorkModels(List<WorkerWorkModel> workerWorkModels) {
        this.workerWorkModels = workerWorkModels;
    }
}
