package com.workflow.data.net.services;

import com.workflow.data.model.ProductModel;
import com.workflow.data.net.responses.GenericMetaResponse;
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
 * Created by Michael on 14/06/19.
 */

public interface ProductService {
    @POST("v1/products")
    Single<GenericResponse> createProduct(@Body ProductModel productModel);

    @GET("v1/products")
    Single<GenericPaginationResponse<List<ProductModel>>> getProducts(
            @Query("article_no") String articleNo
            , @Query("limit") Integer limit
            , @Query("page") Integer page);

    @DELETE("v1/products")
    Single<GenericResponse> deleteProduct(@Query("product_id") List<Integer> productId);

    @PUT("v1/products/{product_id}")
    Single<GenericResponse> updateProduct(@Path("product_id") Integer productId, @Body ProductModel productModel);
}
