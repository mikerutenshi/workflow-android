package com.workflow.data.repository.datasource;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.SearchPaginatedQueryModel;
import com.workflow.data.model.ProductModel;
import com.workflow.data.net.responses.GenericResponse;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michael on 14/06/19.
 */

public interface ProductDataStore {
    Single<GenericItemPaginationModel<List<ProductModel>>> getProducts(SearchPaginatedQueryModel queryModel);
    Single<String> createProduct(ProductModel productModel);
    Single<String> deleteProduct(List<Integer> productId);
    Single<String> updateProduct(ProductModel productModel);
}
