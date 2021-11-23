package com.workflow.domain.interactor.assigned_work;

import com.workflow.data.model.AssignedWorkListModel;
import com.workflow.data.model.RequestModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.AssignedWorkRepository;

import io.reactivex.Single;

public class GetAssignedWorksSource extends SingleUseCase<GenericListResponseModel<AssignedWorkListModel>, RequestModel> {

    private final AssignedWorkRepository repository;

    public GetAssignedWorksSource(AssignedWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<GenericListResponseModel<AssignedWorkListModel>> buildUseCaseSingle(RequestModel params) {
        return repository.getAssignedWorksSource(params);
    }
}
