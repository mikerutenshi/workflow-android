package com.workflow.data.repository;

import com.workflow.data.model.AssignedWorkCreateModel;
import com.workflow.data.model.AssignedWorkListModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.data.model.RequestModel;
import com.workflow.data.repository.datasource.AssignedWorkDataStoreFactory;
import com.workflow.domain.repository.AssignedWorkRepository;

import io.reactivex.Single;

public class AssignedWorkDataRepository implements AssignedWorkRepository {

    private final AssignedWorkDataStoreFactory factory;

    public AssignedWorkDataRepository(AssignedWorkDataStoreFactory factory) {
        this.factory = factory;
    }

    @Override
    public Single<String> createAssignedWork(AssignedWorkCreateModel assignedWorkCreateModel) {
        return factory.create().createAssignedWork(assignedWorkCreateModel);
    }

    @Override
    public Single<GenericListResponseModel<AssignedWorkListModel>> getAssignedWorks(RequestModel requestModel) {
        return factory.create().getAssignedWorks(requestModel);
    }

    @Override
    public Single<GenericListResponseModel<AssignedWorkListModel>> getAssignedWorksSource(RequestModel requestModel) {
        return factory.create().getAssignedWorksSource(requestModel);
    }

    @Override
    public Single<String> deleteAssignedWork(Integer id) {
        return factory.create().deleteAssignedWork(id);
    }
}
