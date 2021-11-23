package com.workflow.domain.interactor.worker;

import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.WorkerRepository;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michael on 12/07/19.
 */

public class DeleteWorker extends SingleUseCase<String, List<Integer>> {

    private final WorkerRepository repository;

    public DeleteWorker(WorkerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<String> buildUseCaseSingle(List<Integer> params) {
        return repository.deleteWorker(params);
    }
}
