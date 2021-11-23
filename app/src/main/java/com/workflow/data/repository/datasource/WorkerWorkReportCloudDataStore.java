package com.workflow.data.repository.datasource;

import com.workflow.data.model.DateFilterModel;
import com.workflow.data.model.WorkerWorkReportModel;
import com.workflow.data.net.responses.GenericResponse;
import com.workflow.data.net.services.WorkerWorkService;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;

/**
 * Created by Michael on 09/07/19.
 */

public class WorkerWorkReportCloudDataStore implements WorkerWorkReportDataStore {

    private final Retrofit retrofit;

    public WorkerWorkReportCloudDataStore(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public Single<WorkerWorkReportModel> getWorkerWorkReport(DateFilterModel filter) {
        return retrofit.create(WorkerWorkService.class).getWorkerWorkReport(
                filter.getStartDate(), filter.getEndDate()
        ).map(new Function<GenericResponse<WorkerWorkReportModel>, WorkerWorkReportModel>() {
            @Override
            public WorkerWorkReportModel apply(GenericResponse<WorkerWorkReportModel> workerWorkReportModelGenericResponse) throws Exception {
                return workerWorkReportModelGenericResponse.getData();
            }
        });
    }
}
