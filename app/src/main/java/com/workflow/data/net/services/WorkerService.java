package com.workflow.data.net.services;

import com.workflow.data.model.ResponseWorkerModel;
import com.workflow.data.model.WorkerModel;
import com.workflow.data.net.responses.GenericPaginationResponse;
import com.workflow.data.net.responses.GenericResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Michael on 27/06/19.
 */

public interface WorkerService {
    @POST("v1/workers")
    Single<GenericResponse> createWorkers(@Body WorkerModel workerModel);

    @GET("v1/workers")
    Single<GenericPaginationResponse<List<WorkerModel>>> getWorkers(
            @Query("name") String name
            , @Query("position") List<String> positions
            , @Query("sort_by") String sortBy
            , @Query("sort_direction") String sortDirection
            , @Query("limit") Integer limit
            , @Query("page") Integer page);

    @DELETE("v1/workers")
    Single<GenericResponse> deleteWorker(@Query("worker_id") List<Integer> workerId);

    @PUT("v1/workers/{worker_id}")
    Single<GenericResponse> updateWorker(@Path("worker_id") Integer workerId, @Body WorkerModel workerModel);
}
