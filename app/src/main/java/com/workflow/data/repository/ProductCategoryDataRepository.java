package com.workflow.data.repository;

import com.workflow.data.model.ListModel;
import com.workflow.data.repository.datasource.ProductCategoryDataStoreFactory;
import com.workflow.domain.repository.ProductCategoryRepository;

import java.util.List;

import io.reactivex.Single;

public class ProductCategoryDataRepository implements ProductCategoryRepository {

    private final ProductCategoryDataStoreFactory factory;

    public ProductCategoryDataRepository(ProductCategoryDataStoreFactory factory) {
        this.factory = factory;
    }

    @Override
    public Single<List<ListModel>> getProductCategories() {
        return factory.create().getProductCategory();
    }
}
