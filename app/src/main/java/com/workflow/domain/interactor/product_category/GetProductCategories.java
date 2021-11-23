package com.workflow.domain.interactor.product_category;

import com.workflow.data.model.ListModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.ProductCategoryRepository;

import java.util.List;

import io.reactivex.Single;

public class GetProductCategories extends SingleUseCase<List<ListModel>, Void> {

    private final ProductCategoryRepository repository;

    public GetProductCategories(ProductCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<List<ListModel>> buildUseCaseSingle(Void params) {
        return repository.getProductCategories();
    }
}
