package com.workflow.data.repository.datasource;

import com.workflow.data.model.ListModel;
import com.workflow.data.net.responses.GenericResponse;
import com.workflow.data.net.services.ProductCategoryService;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;

public class ProductCategoryCloudDataStore implements ProductCategoryDataStore {

    private final Retrofit retrofit;

    public ProductCategoryCloudDataStore(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public Single<List<ListModel>> getProductCategory() {
        return retrofit.create(ProductCategoryService.class).getProductCategories().map(new Function<GenericResponse<List<ListModel>>, List<ListModel>>() {
            @Override
            public List<ListModel> apply(GenericResponse<List<ListModel>> listGenericResponse) throws Exception {
                return listGenericResponse.getData();
            }
        });
    }
}
