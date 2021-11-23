package com.workflow.domain.repository;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.SearchPaginatedQueryModel;
import com.workflow.data.model.ProductModel;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michael on 14/06/19.
 */

public interface ProductRepository {
    Single<GenericItemPaginationModel<List<ProductModel>>> getProducts(SearchPaginatedQueryModel queryModel);
    Single<String> createProduct(ProductModel param);
    Single<String> deleteProduct(List<Integer> productId);
    Single<String> updateProduct(ProductModel productModel);
}
