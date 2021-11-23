package com.workflow.data.net.services;

import com.workflow.data.model.CurrentWorkDetailAssignedModel;
import com.workflow.data.model.CurrentWorkDetailDoneModel;
import com.workflow.data.model.CurrentWorkListModel;
import com.workflow.data.model.WorkModel;
import com.workflow.data.model.WorkUpdateModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.data.net.responses.GenericPaginationResponse;
import com.workflow.data.net.responses.GenericResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Michael on 27/06/19.
 */

public interface WorkService {
    @POST("v1/works")
    Single<GenericResponse> createWorks(@Body WorkModel workerModel);

    @GET("v1/works")
    Single<GenericPaginationResponse<List<WorkModel>>> getWorks(
            @Query("spk_no") String spkNo
            , @Query("limit") Integer limit
            , @Query("page") Integer page);

    @GET("v1-1/works")
    Single<GenericListResponseModel<CurrentWorkListModel>> getWorkList(@QueryMap Map<String, String> options);

    @GET("v1-1/works/{id}/assigned-work")
    Single<GenericListResponseModel<CurrentWorkDetailAssignedModel>> getWorkDetailAssigned(@Path("id") Integer path,
                                                                                           @QueryMap Map<String, String> options);

    @GET("v1-1/works/{id}/done-work")
    Single<GenericListResponseModel<CurrentWorkDetailDoneModel>> getWorkDetailDone(@Path("id") Integer path,
                                                                                   @QueryMap Map<String, String> options);

    @GET("v1/works/{worker_id}")
    Single<GenericPaginationResponse<List<WorkModel>>> getWorksPerWorker(
            @Path("worker_id") Integer workerId
            , @Query("spk_no") String spkNo
            , @Query("limit") Integer limit
            , @Query("page") Integer page);

    @GET("v1/works/unassigned/{worker_id}")
    Single<GenericPaginationResponse<List<WorkModel>>> getUnassignedWork(
            @Path("worker_id") Integer workerId
            , @Query("position") String position
            , @Query("spk_no") String spkNo
            , @Query("limit") Integer limit
            , @Query("page") Integer page
            , @Query("start_date") String startDate
            , @Query("end_date") String endDate
            , @Query("sort_by") String sortBy
            , @Query("sort_direction") String sortDirection);

    @DELETE("v1/works")
    Single<GenericResponse> deleteWork(@Query("work_id") List<Integer> workId);

    @PUT("v1/works/{work_id}")
    Single<GenericResponse> updateWork(@Path("work_id") Integer workId, @Body WorkUpdateModel workModel);
}
