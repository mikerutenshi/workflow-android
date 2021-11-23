package com.workflow.domain.repository;

import com.workflow.data.model.ListModel;

import java.util.List;

import io.reactivex.Single;

public interface ProductCategoryRepository {
    Single<List<ListModel>> getProductCategories();
}
