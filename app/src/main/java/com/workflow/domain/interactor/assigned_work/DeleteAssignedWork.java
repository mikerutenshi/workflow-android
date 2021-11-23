package com.workflow.domain.interactor.assigned_work;

import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.AssignedWorkRepository;

import io.reactivex.Single;

public class DeleteAssignedWork extends SingleUseCase<String, Integer> {

    private final AssignedWorkRepository repository;

    public DeleteAssignedWork(AssignedWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<String> buildUseCaseSingle(Integer params) {
        return repository.deleteAssignedWork(params);
    }
}
