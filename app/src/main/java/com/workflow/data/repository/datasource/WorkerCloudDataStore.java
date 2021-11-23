package com.workflow.data.repository.datasource;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.GetWorkerQueryModel;
import com.workflow.data.model.WorkerModel;
import com.workflow.data.net.responses.GenericPaginationResponse;
import com.workflow.data.net.responses.GenericResponse;
import com.workflow.data.net.services.WorkerService;
import com.workflow.presentation.mapper.WorkerResponseModelMapper;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;

/**
 * Created by Michael on 27/06/19.
 */

public class WorkerCloudDataStore implements WorkersDataStore {

    private final Retrofit retrofit;

    public WorkerCloudDataStore(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public Single<GenericItemPaginationModel<List<WorkerModel>>> getWorkers(GetWorkerQueryModel params) {
        WorkerResponseModelMapper mapper = new WorkerResponseModelMapper();
        return retrofit.create(WorkerService.class).getWorkers(
                params.getSearch()
                , params.getPositions()
                , params.getSortBy()
                , params.getSortDirection()
                , params.getLimit()
                , params.getPage()
        ).map(new Function<GenericPaginationResponse<List<WorkerModel>>, GenericItemPaginationModel<List<WorkerModel>>>() {
            @Override
            public GenericItemPaginationModel<List<WorkerModel>> apply(GenericPaginationResponse<List<WorkerModel>> listGenericPaginationResponse) throws Exception {
                return new GenericItemPaginationModel<List<WorkerModel>>(listGenericPaginationResponse.getData()
                        , listGenericPaginationResponse.getMeta());
            }
        });
    }

    @Override
    public Single<String> createWorker(WorkerModel workerModel) {
        return retrofit.create(WorkerService.class).createWorkers(workerModel).map(new Function<GenericResponse, String>() {
            @Override
            public String apply(GenericResponse genericResponse) throws Exception {
                return genericResponse.getMessage();
            }
        });
    }

    @Override
    public Single<String> deleteWorker(List<Integer> workerId) {
        return retrofit.create(WorkerService.class).deleteWorker(workerId).map(new Function<GenericResponse, String>() {
            @Override
            public String apply(GenericResponse genericResponse) throws Exception {
                return genericResponse.getMessage();
            }
        });
    }

    @Override
    public Single<String> updateWorker(WorkerModel workerModel) {
        return retrofit.create(WorkerService.class).updateWorker(workerModel.getId(), workerModel).map(new Function<GenericResponse, String>() {
            @Override
            public String apply(GenericResponse genericResponse) throws Exception {
                return genericResponse.getMessage();
            }
        });
    }
}
