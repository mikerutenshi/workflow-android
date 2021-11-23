package com.workflow.data.net.services;

import com.workflow.data.model.ListModel;
import com.workflow.data.net.responses.GenericResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ProductCategoryService {
    @GET("v2/product-categories")
    Single<GenericResponse<List<ListModel>>> getProductCategories();
}
