package com.workflow.domain.interactor.product;

import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.ProductRepository;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michael on 12/07/19.
 */

public class DeleteProduct extends SingleUseCase<String, List<Integer>> {

    private final ProductRepository repository;

    public DeleteProduct(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<String> buildUseCaseSingle(List<Integer> params) {
        return repository.deleteProduct(params);
    }
}
