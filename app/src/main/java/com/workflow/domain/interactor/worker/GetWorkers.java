package com.workflow.domain.interactor.worker;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.GetWorkerQueryModel;
import com.workflow.data.model.SearchPaginatedQueryModel;
import com.workflow.data.model.WorkerModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.WorkerRepository;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michael on 27/06/19.
 */

public class GetWorkers extends SingleUseCase<GenericItemPaginationModel<List<WorkerModel>>, GetWorkerQueryModel> {

    private final WorkerRepository repository;

    public GetWorkers(WorkerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<GenericItemPaginationModel<List<WorkerModel>>> buildUseCaseSingle(GetWorkerQueryModel params) {
        return repository.getWorkers(params);
    }
}
