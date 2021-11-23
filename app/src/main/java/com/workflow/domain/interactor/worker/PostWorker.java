package com.workflow.domain.interactor.worker;

import com.workflow.data.model.WorkerModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.WorkRepository;
import com.workflow.domain.repository.WorkerRepository;

import io.reactivex.Single;

/**
 * Created by Michael on 27/06/19.
 */

public class PostWorker extends SingleUseCase<String, WorkerModel> {

    private final WorkerRepository repository;

    public PostWorker(WorkerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<String> buildUseCaseSingle(WorkerModel params) {
        return repository.createWorker(params);
    }
}
