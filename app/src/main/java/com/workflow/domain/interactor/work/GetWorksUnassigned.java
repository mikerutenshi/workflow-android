package com.workflow.domain.interactor.work;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.GetUnassignedWorkQueryModel;
import com.workflow.data.model.WorkModel;
import com.workflow.data.model.WorkQueryModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.WorkRepository;

import java.util.List;

import io.reactivex.Single;

public class GetWorksUnassigned extends SingleUseCase<GenericItemPaginationModel<List<WorkModel>>, GetUnassignedWorkQueryModel> {
    private final WorkRepository repository;

    public GetWorksUnassigned(WorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<GenericItemPaginationModel<List<WorkModel>>> buildUseCaseSingle(GetUnassignedWorkQueryModel params) {
        return repository.getUnassignedWorks(params);
    }
}
