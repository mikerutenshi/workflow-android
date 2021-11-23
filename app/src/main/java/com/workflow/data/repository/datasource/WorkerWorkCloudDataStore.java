package com.workflow.data.repository.datasource;

import com.workflow.data.model.CreateWorkerWorkModel;
import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.RequestModel;
import com.workflow.data.model.WorkerWorkDeleteModel;
import com.workflow.data.model.WorkerWorkModel;
import com.workflow.data.net.responses.GenericPaginationResponse;
import com.workflow.data.net.responses.GenericResponse;
import com.workflow.data.net.services.WorkerWorkService;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;

/**
 * Created by Michael on 08/07/19.
 */

public class WorkerWorkCloudDataStore implements WorkerWorkDataStore {

    private final Retrofit retrofit;

    public WorkerWorkCloudDataStore(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public Single<String> createWorkerWork(CreateWorkerWorkModel createWorkerWorkModel) {
        return retrofit.create(WorkerWorkService.class).createWorkerWork(
                createWorkerWorkModel.getWorkerPos(), createWorkerWorkModel.getWorkerWorkModels()
        ).map(new Function<GenericResponse, String>() {
            @Override
            public String apply(GenericResponse genericResponse) throws Exception {
                return genericResponse.getMessage();
            }
        });
    }

    @Override
    public Single<GenericItemPaginationModel<List<WorkerWorkModel>>> getWorkerworks(RequestModel requestModel) {
        return retrofit.create(WorkerWorkService.class).getWorkerWorksPerWorker(
                requestModel.getPath(), requestModel.getQueries()
        ).map(new Function<GenericPaginationResponse<List<WorkerWorkModel>>, GenericItemPaginationModel<List<WorkerWorkModel>>>() {
            @Override
            public GenericItemPaginationModel<List<WorkerWorkModel>> apply(GenericPaginationResponse<List<WorkerWorkModel>> listGenericPaginationResponse) throws Exception {
                return new GenericItemPaginationModel<>(listGenericPaginationResponse.getData()
                        , listGenericPaginationResponse.getMeta());
            }
        });
    }

    @Override
    public Single<String> deleteWorkerWork(WorkerWorkDeleteModel params) {
        return retrofit.create(WorkerWorkService.class).deleteWorkerWork(
                params.getWorkerId(), params.getWorkId(), params.getPosition()
        ).map(new Function<GenericResponse, String>() {
            @Override
            public String apply(GenericResponse genericResponse) throws Exception {
                return genericResponse.getMessage();
            }
        });
    }
}
