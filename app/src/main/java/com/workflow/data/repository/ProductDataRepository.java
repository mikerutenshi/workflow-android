package com.workflow.data.repository;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.SearchPaginatedQueryModel;
import com.workflow.data.model.ProductModel;
import com.workflow.data.repository.datasource.ProductDataStoreFactory;
import com.workflow.domain.repository.ProductRepository;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michael on 14/06/19.
 */

public class ProductDataRepository implements ProductRepository {

    private final ProductDataStoreFactory factory;

    public ProductDataRepository(ProductDataStoreFactory factory) {
        this.factory = factory;
    }

    @Override
    public Single<GenericItemPaginationModel<List<ProductModel>>> getProducts(SearchPaginatedQueryModel queryModel) {
        return factory.create().getProducts(queryModel);
    }

    @Override
    public Single<String> createProduct(ProductModel param) {
        return factory.create().createProduct(param);
    }

    @Override
    public Single<String> deleteProduct(List<Integer> productId) {
        return factory.create().deleteProduct(productId);
    }

    @Override
    public Single<String> updateProduct(ProductModel productModel) {
        return factory.create().updateProduct(productModel);
    }
}
