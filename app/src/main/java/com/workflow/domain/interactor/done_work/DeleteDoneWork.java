package com.workflow.domain.interactor.done_work;

import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.DoneWorkRepository;

import io.reactivex.Single;

public class DeleteDoneWork extends SingleUseCase<String, Integer> {
    private final DoneWorkRepository repository;

    public DeleteDoneWork(DoneWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<String> buildUseCaseSingle(Integer params) {
        return repository.deleteDoneWork(params);
    }
}
