package com.workflow.domain.interactor.product;

import com.workflow.data.model.ProductModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.ProductRepository;

import io.reactivex.Single;

/**
 * Created by Michael on 18/06/19.
 */

public class PostProduct extends SingleUseCase<String, ProductModel> {

    private final ProductRepository repository;

    public PostProduct(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<String> buildUseCaseSingle(ProductModel params) {
        return repository.createProduct(params);
    }
}
