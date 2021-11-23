package com.workflow.data.net.services;

import com.workflow.data.model.DoneWorkCreateModel;
import com.workflow.data.model.DoneWorkListModel;
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

public interface DoneWorkService {
    @POST("v1/done-works")
    Single<GenericResponse> create(@Body DoneWorkCreateModel doneWorkCreateModel);

    @GET("v1/done-works/{id}")
    Single<GenericListResponseModel<DoneWorkListModel>> getAll(@Path("id") Integer path, @QueryMap Map<String,String> queries);

    @GET("v1/done-works/{worker_id}/doneables")
    Single<GenericListResponseModel<DoneWorkListModel>> getDoneables(@Path("worker_id") Integer path, @QueryMap Map<String,String> queries);

    @DELETE("v1/done-works/{id}")
    Single<GenericResponse> delete(@Path("id") Integer path);
}
