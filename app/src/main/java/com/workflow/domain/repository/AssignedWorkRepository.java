package com.workflow.domain.repository;

import com.workflow.data.model.AssignedWorkCreateModel;
import com.workflow.data.model.AssignedWorkListModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.data.model.RequestModel;

import io.reactivex.Single;

public interface AssignedWorkRepository {
    Single<String> createAssignedWork(AssignedWorkCreateModel assignedWorkCreateModel);
    Single<GenericListResponseModel<AssignedWorkListModel>> getAssignedWorks(RequestModel requestModel);
    Single<GenericListResponseModel<AssignedWorkListModel>> getAssignedWorksSource(RequestModel requestModel);
    Single<String> deleteAssignedWork(Integer id);
}
