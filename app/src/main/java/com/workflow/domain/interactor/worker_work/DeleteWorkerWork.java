package com.workflow.domain.interactor.worker_work;

import com.workflow.data.model.WorkerWorkDeleteModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.WorkerWorkRepository;

import io.reactivex.Single;

/**
 * Created by Michael on 12/07/19.
 */

public class DeleteWorkerWork extends SingleUseCase<String, WorkerWorkDeleteModel> {

    private final WorkerWorkRepository repository;

    public DeleteWorkerWork(WorkerWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<String> buildUseCaseSingle(WorkerWorkDeleteModel params) {
        return repository.deleteWorkerWork(params);
    }
}
