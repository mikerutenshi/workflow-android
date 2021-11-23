package com.workflow.data.repository.datasource;

import com.workflow.data.model.ListModel;

import java.util.List;

import io.reactivex.Single;

public interface ProductCategoryDataStore {
    Single<List<ListModel>> getProductCategory();
}
