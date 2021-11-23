package com.workflow.data.net.services;

import com.workflow.data.model.SalaryReportDetailListModel;
import com.workflow.data.model.SalaryReportListModel;
import com.workflow.data.net.responses.GenericResponseModel;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface SalaryReportService {
    @GET("v1/salary-report")
    Single<GenericResponseModel<SalaryReportListModel>> getSalaryReportList(@QueryMap Map<String, String> options);
    @GET("v1/salary-report/{worker_id}")
    Single<GenericResponseModel<SalaryReportDetailListModel>> getSalaryReportDetailList(
            @Path("worker_id") Integer path, @QueryMap Map<String, String> options);
}
