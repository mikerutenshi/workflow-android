package com.workflow.data.repository.datasource;

import com.workflow.data.model.RequestModel;
import com.workflow.data.model.SalaryReportDetailListModel;
import com.workflow.data.model.SalaryReportListModel;
import com.workflow.data.net.responses.GenericResponseModel;
import com.workflow.data.net.services.SalaryReportService;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.Retrofit;

public class SalaryReportListCloudDataStore implements SalaryReportListDataStore {
    private final Retrofit retrofit;

    public SalaryReportListCloudDataStore(Retrofit retrofit) {
        this.retrofit = retrofit;
    }


    @Override
    public Single<GenericResponseModel<SalaryReportListModel>> getSalaryReportList(Map<String, String> options) {
        return retrofit.create(SalaryReportService.class).getSalaryReportList(options);
    }

    @Override
    public Single<GenericResponseModel<SalaryReportDetailListModel>> getSalaryReportDetailList(RequestModel params) {
        return retrofit.create(SalaryReportService.class).getSalaryReportDetailList(params.getPath(), params.getQueries());
    }
}
