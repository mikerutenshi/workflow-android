package com.workflow.data.net.services;

import com.workflow.data.model.WorkDetailModel;
import com.workflow.data.model.WorkerWorkModel;
import com.workflow.data.model.WorkerWorkReportModel;
import com.workflow.data.net.responses.GenericPaginationResponse;
import com.workflow.data.net.responses.GenericResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Michael on 08/07/19.
 */

public interface WorkerWorkService {
    @POST("v1/workerworks")
    Single<GenericResponse> createWorkerWork(@Query("position") String position, @Body List<WorkerWorkModel> workerWorkModels);

    @GET("v1/workerreport")
    Single<GenericResponse<WorkerWorkReportModel>> getWorkerWorkReport(
            @Query("start_date") String startDate
            , @Query("end_date") String endDate);

    @GET("v1/workerreport/{worker_id}")
    Single<GenericResponse<List<WorkDetailModel>>> getWorkDetailReport(
            @Path("worker_id") Integer workerId
            , @Query("start_date") String startDate
            , @Query("end_date") String endDate
            , @Query("position") String position);

    @GET("v1/workerworks/{worker_id}")
    Single<GenericPaginationResponse<List<WorkerWorkModel>>> getWorkerWorksPerWorker(
            @Path("worker_id") Integer workerId
            , @QueryMap Map<String, String> options);

    @DELETE("v1/workerworks")
    Single<GenericResponse> deleteWorkerWork(@Query("worker_id") Integer workerId
            , @Query("work_id") Integer workId, @Query("position") String pos);
}
