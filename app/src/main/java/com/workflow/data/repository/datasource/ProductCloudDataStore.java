package com.workflow.data.repository.datasource;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.ProductModel;
import com.workflow.data.model.SearchPaginatedQueryModel;
import com.workflow.data.net.responses.GenericPaginationResponse;
import com.workflow.data.net.responses.GenericResponse;
import com.workflow.data.net.services.ProductService;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;

/**
 * Created by Michael on 14/06/19.
 */

public class ProductCloudDataStore implements ProductDataStore {

    private final Retrofit retrofit;

    public ProductCloudDataStore(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public Single<GenericItemPaginationModel<List<ProductModel>>> getProducts(SearchPaginatedQueryModel queryModel) {
        String articleNo = queryModel == null ? null : queryModel.getSearch();
        Integer limit = queryModel == null ? null : queryModel.getLimit();
        Integer page = queryModel == null ? null : queryModel.getPage();
        return retrofit.create(ProductService.class).getProducts(articleNo, limit, page).map(new Function<GenericPaginationResponse<List<ProductModel>>, GenericItemPaginationModel<List<ProductModel>>>() {
            @Override
            public GenericItemPaginationModel<List<ProductModel>> apply(GenericPaginationResponse<List<ProductModel>> listGenericPaginationResponse) throws Exception {
                return new GenericItemPaginationModel<>(listGenericPaginationResponse.getData(), listGenericPaginationResponse.getMeta());
            }
        });
    }

    @Override
    public Single<String> createProduct(ProductModel productModel) {
        return retrofit.create(ProductService.class).createProduct(productModel).map(new Function<GenericResponse, String>() {
            @Override
            public String apply(GenericResponse genericResponse) throws Exception {
                return genericResponse.getMessage();
            }
        });
    }

    @Override
    public Single<String> deleteProduct(List<Integer> productIds) {
        return retrofit.create(ProductService.class).deleteProduct(productIds).map(new Function<GenericResponse, String>() {
            @Override
            public String apply(GenericResponse genericResponse) throws Exception {
                return genericResponse.getMessage();
            }
        });
    }

    @Override
    public Single<String> updateProduct(ProductModel productModel) {
        return retrofit.create(ProductService.class).updateProduct(productModel.getId(), productModel).map(new Function<GenericResponse, String>() {
            @Override
            public String apply(GenericResponse genericResponse) throws Exception {
                return genericResponse.getMessage();
            }
        });
    }
}
