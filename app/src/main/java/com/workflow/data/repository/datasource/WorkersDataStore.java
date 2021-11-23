package com.workflow.data.repository.datasource;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.GetWorkerQueryModel;
import com.workflow.data.model.SearchPaginatedQueryModel;
import com.workflow.data.model.WorkerModel;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michael on 27/06/19.
 */

public interface WorkersDataStore {
    Single<GenericItemPaginationModel<List<WorkerModel>>> getWorkers(GetWorkerQueryModel params);
    Single<String> createWorker(WorkerModel workerModel);
    Single<String> deleteWorker(List<Integer> workerId);
    Single<String> updateWorker(WorkerModel workerModel);
}
