package com.workflow.data.repository;

import com.workflow.data.model.CreateWorkerWorkModel;
import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.RequestModel;
import com.workflow.data.model.WorkerWorkDeleteModel;
import com.workflow.data.model.WorkerWorkModel;
import com.workflow.data.model.WorkerWorkQueryModel;
import com.workflow.data.repository.datasource.WorkerWorkDataStoreFactory;
import com.workflow.domain.repository.WorkerWorkRepository;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michael on 08/07/19.
 */

public class WorkerWorkDataRepository implements WorkerWorkRepository {

    private final WorkerWorkDataStoreFactory factory;

    public WorkerWorkDataRepository(WorkerWorkDataStoreFactory factory) {
        this.factory = factory;
    }

    @Override
    public Single<String> createWorkerWork(CreateWorkerWorkModel param) {
        return factory.create().createWorkerWork(param);
    }

    @Override
    public Single<String> deleteWorkerWork(WorkerWorkDeleteModel params) {
        return factory.create().deleteWorkerWork(params);
    }

    @Override
    public Single<GenericItemPaginationModel<List<WorkerWorkModel>>> getWorkerWork(RequestModel requestModel) {
        return factory.create().getWorkerworks(requestModel);
    }
}
