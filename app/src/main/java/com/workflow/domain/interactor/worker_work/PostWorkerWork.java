package com.workflow.domain.interactor.worker_work;

import com.workflow.data.model.CreateWorkerWorkModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.WorkerWorkRepository;

import io.reactivex.Single;

/**
 * Created by Michael on 08/07/19.
 */

public class PostWorkerWork extends SingleUseCase<String, CreateWorkerWorkModel> {

    private final WorkerWorkRepository repository;

    public PostWorkerWork(WorkerWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<String> buildUseCaseSingle(CreateWorkerWorkModel params) {
        return repository.createWorkerWork(params);
    }
}
