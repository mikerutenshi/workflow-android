package com.workflow.domain.interactor.work;

import com.workflow.data.model.WorkModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.WorkRepository;

import io.reactivex.Single;

/**
 * Created by Michael on 28/06/19.
 */

public class PostWork extends SingleUseCase<String, WorkModel> {

    private final WorkRepository repository;

    public PostWork(WorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<String> buildUseCaseSingle(WorkModel params) {
        return repository.createWork(params);
    }
}
