package com.workflow.domain.interactor.work;

import com.workflow.data.model.WorkModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.WorkRepository;

import io.reactivex.Single;

public class UpdateWork extends SingleUseCase<String, WorkModel> {

    private final WorkRepository repository;

    public UpdateWork(WorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<String> buildUseCaseSingle(WorkModel params) {
        return repository.updateWork(params);
    }
}
