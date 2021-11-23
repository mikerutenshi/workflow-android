package com.workflow.data.repository;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.GetWorkerQueryModel;
import com.workflow.data.model.SearchPaginatedQueryModel;
import com.workflow.data.model.WorkerModel;
import com.workflow.data.repository.datasource.WorkerDataStoreFactory;
import com.workflow.domain.repository.WorkerRepository;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michael on 27/06/19.
 */

public class WorkerDataRepository implements WorkerRepository {

    private final WorkerDataStoreFactory factory;

    public WorkerDataRepository(WorkerDataStoreFactory factory) {
        this.factory = factory;
    }

    @Override
    public Single<GenericItemPaginationModel<List<WorkerModel>>> getWorkers(GetWorkerQueryModel params) {
        return factory.create().getWorkers(params);
    }

    @Override
    public Single<String> createWorker(WorkerModel param) {
        return factory.create().createWorker(param);
    }

    @Override
    public Single<String> deleteWorker(List<Integer> workerId) {
        return factory.create().deleteWorker(workerId);
    }

    @Override
    public Single<String> updateWorker(WorkerModel workerModel) {
        return factory.create().updateWorker(workerModel);
    }
}
