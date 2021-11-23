package com.workflow.data.repository.datasource;

import com.workflow.data.model.AssignedWorkCreateModel;
import com.workflow.data.model.AssignedWorkListModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.data.model.RequestModel;
import com.workflow.data.net.responses.GenericResponse;
import com.workflow.data.net.services.AssignedWorkService;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class AssignedWorkCloudDataStore implements AssignedWorkDataStore {

    private final Retrofit retrofit;

    public AssignedWorkCloudDataStore(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public Single<String> createAssignedWork(AssignedWorkCreateModel assignedWorkCreateModel) {
        return retrofit.create(AssignedWorkService.class).create(assignedWorkCreateModel).map(GenericResponse::getMessage);
    }

    @Override
    public Single<GenericListResponseModel<AssignedWorkListModel>> getAssignedWorks(RequestModel requestModel) {
        return retrofit.create(AssignedWorkService.class).getAll(requestModel.getPath(), requestModel.getQueries());
    }

    @Override
    public Single<GenericListResponseModel<AssignedWorkListModel>> getAssignedWorksSource(RequestModel requestModel) {
        return retrofit.create(AssignedWorkService.class).getAllSource(requestModel.getQueries());
    }

    @Override
    public Single<String> deleteAssignedWork(Integer id) {
        return retrofit.create(AssignedWorkService.class).delete(id).map(GenericResponse::getMessage);
    }
}
