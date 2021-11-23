package com.workflow.domain.interactor.worker_work;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.RequestModel;
import com.workflow.data.model.WorkerWorkModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.WorkerWorkRepository;

import java.util.List;

import io.reactivex.Single;

public class GetWorkerWork extends SingleUseCase<GenericItemPaginationModel<List<WorkerWorkModel>>
        , RequestModel> {

    private final WorkerWorkRepository repository;

    public GetWorkerWork(WorkerWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<GenericItemPaginationModel<List<WorkerWorkModel>>> buildUseCaseSingle(RequestModel params) {
        return repository.getWorkerWork(params);
    }
}
