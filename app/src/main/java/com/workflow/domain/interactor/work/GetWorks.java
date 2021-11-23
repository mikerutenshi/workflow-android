package com.workflow.domain.interactor.work;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.SearchPaginatedQueryModel;
import com.workflow.data.model.WorkModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.WorkRepository;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michael on 28/06/19.
 */

public class GetWorks extends SingleUseCase<GenericItemPaginationModel<List<WorkModel>>, SearchPaginatedQueryModel> {

    private final WorkRepository repository;

    public GetWorks(WorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<GenericItemPaginationModel<List<WorkModel>>> buildUseCaseSingle(SearchPaginatedQueryModel params) {
        return repository.getWorks(params);
    }
}
