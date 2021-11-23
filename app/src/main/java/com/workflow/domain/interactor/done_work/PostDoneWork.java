package com.workflow.domain.interactor.done_work;

import com.workflow.data.model.DoneWorkCreateModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.DoneWorkRepository;

import io.reactivex.Single;

public class PostDoneWork extends SingleUseCase<String, DoneWorkCreateModel> {
    private final DoneWorkRepository repository;

    public PostDoneWork(DoneWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<String> buildUseCaseSingle(DoneWorkCreateModel params) {
        return repository.createDoneWork(params);
    }
}
