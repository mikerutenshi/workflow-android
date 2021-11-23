package com.workflow.domain.interactor.assigned_work;

import com.workflow.data.model.AssignedWorkCreateModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.AssignedWorkRepository;

import io.reactivex.Single;

public class PostAssignedWork extends SingleUseCase<String, AssignedWorkCreateModel> {

    private AssignedWorkRepository repository;

    public PostAssignedWork(AssignedWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<String> buildUseCaseSingle(AssignedWorkCreateModel params) {
        return repository.createAssignedWork(params);
    }
}
