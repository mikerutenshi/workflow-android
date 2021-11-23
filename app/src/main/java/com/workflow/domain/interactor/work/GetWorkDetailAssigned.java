package com.workflow.domain.interactor.work;

import com.workflow.data.model.CurrentWorkDetailAssignedModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.WorkRepository;

import java.util.HashMap;

import io.reactivex.Single;

public class GetWorkDetailAssigned extends SingleUseCase<GenericListResponseModel<CurrentWorkDetailAssignedModel>, HashMap<String, String>> {

    private final WorkRepository repository;

    public GetWorkDetailAssigned(WorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<GenericListResponseModel<CurrentWorkDetailAssignedModel>> buildUseCaseSingle(HashMap<String, String> params) {
        return repository.getWorkDetailAssigned(params);
    }
}
