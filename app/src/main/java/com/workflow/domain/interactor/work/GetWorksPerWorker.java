package com.workflow.domain.interactor.work;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.WorkModel;
import com.workflow.data.model.WorkQueryModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.WorkRepository;

import java.util.List;

import io.reactivex.Single;

public class GetWorksPerWorker extends SingleUseCase<GenericItemPaginationModel<List<WorkModel>>, WorkQueryModel> {
    private final WorkRepository repository;

    public GetWorksPerWorker(WorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<GenericItemPaginationModel<List<WorkModel>>> buildUseCaseSingle(WorkQueryModel params) {
        return repository.getWorksPerWorker(params);
    }
}
