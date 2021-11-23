package com.workflow.presentation.presenter;

import com.workflow.data.model.WorkerModel;

/**
 * Created by Michael on 29/06/19.
 */

public interface WorkerCreatePresenter extends BasePresenter {
    void postWorker(WorkerModel param);
    void updateWorker(WorkerModel workerModel);
}
