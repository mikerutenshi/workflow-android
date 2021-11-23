package com.workflow.data.net.services;

import com.workflow.data.model.AssignedWorkCreateModel;
import com.workflow.data.model.AssignedWorkListModel;
import com.workflow.data.net.responses.GenericListResponseModel;
import com.workflow.data.net.responses.GenericResponse;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface AssignedWorkService {
    @POST("v1/assigned-works")
    Single<GenericResponse> create(@Body AssignedWorkCreateModel assignedWorkCreateModel);

    @GET("v1/assigned-works/{id}")
    Single<GenericListResponseModel<AssignedWorkListModel>> getAll(@Path("id") Integer path, @QueryMap Map<String,String> queries);

    @GET("v1/assigned-works")
    Single<GenericListResponseModel<AssignedWorkListModel>> getAllSource(@QueryMap Map<String,String> queries);

    @DELETE("v1/assigned-works/{id}")
    Single<GenericResponse> delete(@Path("id") Integer path);
}
