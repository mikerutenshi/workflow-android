package com.workflow.data.repository.datasource;

import com.workflow.data.model.CurrentWorkDetailAssignedModel;
import com.workflow.data.model.CurrentWorkDetailDoneModel;
import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.GetUnassignedWorkQueryModel;
import com.workflow.data.model.SearchPaginatedQueryModel;
import com.workflow.data.model.CurrentWorkListModel;
import com.workflow.data.model.WorkModel;
import com.workflow.data.model.WorkQueryModel;
import com.workflow.data.model.WorkUpdateModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.data.net.responses.GenericPaginationResponse;
import com.workflow.data.net.responses.GenericResponse;
import com.workflow.data.net.services.WorkService;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;

/**
 * Created by Michael on 27/06/19.
 */

public class WorkCloudDataStore implements WorkDataStore {

    private final Retrofit retrofit;

    public WorkCloudDataStore(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public Single<GenericItemPaginationModel<List<WorkModel>>> getWorks(SearchPaginatedQueryModel params) {
        String spkNo = params == null ? null : params.getSearch();
        Integer limit = params == null ? null : params.getLimit();
        Integer page = params == null ? null : params.getPage();
        return retrofit.create(WorkService.class).getWorks(spkNo, limit, page).map(new Function<GenericPaginationResponse<List<WorkModel>>, GenericItemPaginationModel<List<WorkModel>>>() {
            @Override
            public GenericItemPaginationModel<List<WorkModel>> apply(GenericPaginationResponse<List<WorkModel>> listGenericPaginationResponse) throws Exception {
                return new GenericItemPaginationModel<>(listGenericPaginationResponse.getData()
                        , listGenericPaginationResponse.getMeta());
            }
        });
    }

    @Override
    public Single<GenericItemPaginationModel<List<WorkModel>>> getWorksPerWorker(WorkQueryModel params) {
        String spkNo = params == null ? null : params.getSearch();
        Integer limit = params == null ? null : params.getLimit();
        Integer page = params == null ? null : params.getPage();
        Integer workerId = params == null ? null : params.getWorkerId();
        return retrofit.create(WorkService.class).getWorksPerWorker(workerId, spkNo, limit, page).map(new Function<GenericPaginationResponse<List<WorkModel>>, GenericItemPaginationModel<List<WorkModel>>>() {
            @Override
            public GenericItemPaginationModel<List<WorkModel>> apply(GenericPaginationResponse<List<WorkModel>> listGenericPaginationResponse) throws Exception {
                return new GenericItemPaginationModel<>(listGenericPaginationResponse.getData()
                        , listGenericPaginationResponse.getMeta());
            }
        });
    }

    @Override
    public Single<GenericItemPaginationModel<List<WorkModel>>> getUnassignedWorks(GetUnassignedWorkQueryModel params) {
        return retrofit.create(WorkService.class).getUnassignedWork(
                params.getWorkerId()
                , params.getWorkerPos()
                , params.getSearch()
                , params.getLimit()
                , params.getPage()
                , params.getStartDate()
                , params.getEndDate()
                , params.getSortBy()
                , params.getSortDirection()
        ).map(new Function<GenericPaginationResponse<List<WorkModel>>, GenericItemPaginationModel<List<WorkModel>>>() {
            @Override
            public GenericItemPaginationModel<List<WorkModel>> apply(GenericPaginationResponse<List<WorkModel>> listGenericPaginationResponse) throws Exception {
                return new GenericItemPaginationModel<>(listGenericPaginationResponse.getData()
                        , listGenericPaginationResponse.getMeta());
            }
        });
    }

    @Override
    public Single<String> createWork(WorkModel workerModel) {
        return retrofit.create(WorkService.class).createWorks(workerModel).map(new Function<GenericResponse, String>() {
            @Override
            public String apply(GenericResponse genericResponse) throws Exception {
                return genericResponse.getMessage();
            }
        });
    }

    @Override
    public Single<String> deleteWork(List<Integer> workId) {
        return retrofit.create(WorkService.class).deleteWork(workId).map(new Function<GenericResponse, String>() {
            @Override
            public String apply(GenericResponse genericResponse) throws Exception {
                return genericResponse.getMessage();
            }
        });
    }

    @Override
    public Single<String> updateWork(WorkModel workModel) {
        return retrofit.create(WorkService.class).updateWork(workModel.getId(),
                new WorkUpdateModel(workModel.getSpkNo(),
                        workModel.getProductId(),
                        workModel.getQty(),
                        workModel.getNotes())).map(new Function<GenericResponse, String>() {
            @Override
            public String apply(GenericResponse genericResponse) throws Exception {
                return genericResponse.getMessage();
            }
        });
    }

    @Override
    public Single<GenericListResponseModel<CurrentWorkListModel>> getWorkList(HashMap<String, String> options) {
        return retrofit.create(WorkService.class).getWorkList(options);
    }

    @Override
    public Single<GenericListResponseModel<CurrentWorkDetailAssignedModel>> getWorkDetailAssigned(HashMap<String, String> options) {
        int workId = Integer.valueOf(options.get("work_id"));
        HashMap<String, String> params = new HashMap<>(options);
        params.remove("work_id");

        return retrofit.create(WorkService.class).getWorkDetailAssigned(workId, params);
    }

    @Override
    public Single<GenericListResponseModel<CurrentWorkDetailDoneModel>> getWorkDetailDone(HashMap<String, String> options) {
        int workId = Integer.valueOf(options.get("work_id"));
        HashMap<String, String> params = new HashMap<>(options);
        params.remove("work_id");
        return retrofit.create(WorkService.class).getWorkDetailDone(workId, params);
    }
}
