package com.workflow.data.repository.datasource;

import com.workflow.data.model.WorkDetailModel;
import com.workflow.data.model.ReportDetailDateFilterModel;
import com.workflow.data.net.responses.GenericResponse;
import com.workflow.data.net.services.WorkerWorkService;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;

/**
 * Created by Michael on 25/07/19.
 */

public class WorkDetailReportCloudDataStore implements WorkDetailReportDataStore  {

    private final Retrofit retrofit;

    public WorkDetailReportCloudDataStore(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public Single<List<WorkDetailModel>> getWorkDetailReport(ReportDetailDateFilterModel filterModel) {
        return retrofit.create(WorkerWorkService.class).getWorkDetailReport(
                filterModel.getWorkerId(),
                filterModel.getStartDate(),
                filterModel.getEndDate(),
                filterModel.getPosition()
        ).map(new Function<GenericResponse<List<WorkDetailModel>>, List<WorkDetailModel>>() {
            @Override
            public List<WorkDetailModel> apply(GenericResponse<List<WorkDetailModel>> listGenericResponse) throws Exception {
                return listGenericResponse.getData();
            }
        });
    }
}
