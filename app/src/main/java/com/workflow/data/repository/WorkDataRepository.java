package com.workflow.data.repository;

import com.workflow.data.model.CurrentWorkDetailAssignedModel;
import com.workflow.data.model.CurrentWorkDetailDoneModel;
import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.GetUnassignedWorkQueryModel;
import com.workflow.data.model.SearchPaginatedQueryModel;
import com.workflow.data.model.CurrentWorkListModel;
import com.workflow.data.model.WorkModel;
import com.workflow.data.model.WorkQueryModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.data.repository.datasource.WorkDataStoreFactory;
import com.workflow.domain.repository.WorkRepository;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michael on 27/06/19.
 */

public class WorkDataRepository implements WorkRepository {

    private final WorkDataStoreFactory factory;

    public WorkDataRepository(WorkDataStoreFactory factory) {
        this.factory = factory;
    }

    @Override
    public Single<GenericItemPaginationModel<List<WorkModel>>> getWorks(SearchPaginatedQueryModel params) {
        return factory.create().getWorks(params);
    }

    @Override
    public Single<GenericItemPaginationModel<List<WorkModel>>> getWorksPerWorker(WorkQueryModel params) {
        return factory.create().getWorksPerWorker(params);
    }

    @Override
    public Single<GenericItemPaginationModel<List<WorkModel>>> getUnassignedWorks(GetUnassignedWorkQueryModel params) {
        return factory.create().getUnassignedWorks(params);
    }

    @Override
    public Single<String> createWork(WorkModel param) {
        return factory.create().createWork(param);
    }

    @Override
    public Single<String> deleteWork(List<Integer> workId) {
        return factory.create().deleteWork(workId);
    }

    @Override
    public Single<String> updateWork(WorkModel workModel) {
        return factory.create().updateWork(workModel);
    }

    @Override
    public Single<GenericListResponseModel<CurrentWorkListModel>> getWorkList(HashMap<String, String> options) {
        return factory.create().getWorkList(options);
    }

    @Override
    public Single<GenericListResponseModel<CurrentWorkDetailAssignedModel>> getWorkDetailAssigned(HashMap<String, String> options) {
        return factory.create().getWorkDetailAssigned(options);
    }

    @Override
    public Single<GenericListResponseModel<CurrentWorkDetailDoneModel>> getWorkDetailDone(HashMap<String, String> options) {
        return factory.create().getWorkDetailDone(options);
    }
}
