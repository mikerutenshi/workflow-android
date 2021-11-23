package com.workflow.domain.repository;

import com.workflow.data.model.CurrentWorkDetailAssignedModel;
import com.workflow.data.model.CurrentWorkDetailDoneModel;
import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.GetUnassignedWorkQueryModel;
import com.workflow.data.model.SearchPaginatedQueryModel;
import com.workflow.data.model.CurrentWorkListModel;
import com.workflow.data.model.WorkModel;
import com.workflow.data.model.WorkQueryModel;
import com.workflow.data.net.responses.GenericListResponseModel;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michael on 27/06/19.
 */

public interface WorkRepository {
    Single<GenericItemPaginationModel<List<WorkModel>>> getWorks(SearchPaginatedQueryModel params);
    Single<GenericItemPaginationModel<List<WorkModel>>> getWorksPerWorker(WorkQueryModel params);
    Single<GenericItemPaginationModel<List<WorkModel>>> getUnassignedWorks(GetUnassignedWorkQueryModel params);
    Single<String> createWork(WorkModel param);
    Single<String> deleteWork(List<Integer> workId);
    Single<String> updateWork(WorkModel workModel);
    Single<GenericListResponseModel<CurrentWorkListModel>> getWorkList(HashMap<String, String> options);
    Single<GenericListResponseModel<CurrentWorkDetailAssignedModel>> getWorkDetailAssigned(HashMap<String, String> options);
    Single<GenericListResponseModel<CurrentWorkDetailDoneModel>> getWorkDetailDone(HashMap<String, String> options);
}
