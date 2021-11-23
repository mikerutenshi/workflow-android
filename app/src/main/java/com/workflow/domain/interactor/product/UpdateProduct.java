package com.workflow.domain.interactor.product;

import com.workflow.data.model.ProductModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.ProductRepository;

import io.reactivex.Single;

public class UpdateProduct extends SingleUseCase<String, ProductModel> {

    private final ProductRepository repository;

    public UpdateProduct(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<String> buildUseCaseSingle(ProductModel params) {
        return repository.updateProduct(params);
    }
}
