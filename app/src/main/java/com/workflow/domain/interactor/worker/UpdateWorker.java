package com.workflow.domain.interactor.worker;

import com.workflow.data.model.WorkerModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.WorkerRepository;

import io.reactivex.Single;

public class UpdateWorker extends SingleUseCase<String, WorkerModel> {

    private final WorkerRepository repository;

    public UpdateWorker(WorkerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<String> buildUseCaseSingle(WorkerModel params) {
        return repository.updateWorker(params);
    }
}
