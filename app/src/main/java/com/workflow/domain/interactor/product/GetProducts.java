package com.workflow.domain.interactor.product;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.SearchPaginatedQueryModel;
import com.workflow.data.model.ProductModel;
import com.workflow.domain.interactor.SingleUseCase;
import com.workflow.domain.repository.ProductRepository;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michael on 14/06/19.
 */

public class GetProducts extends SingleUseCase<GenericItemPaginationModel<List<ProductModel>>, SearchPaginatedQueryModel>{

    private final ProductRepository repository;

    public GetProducts(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<GenericItemPaginationModel<List<ProductModel>>> buildUseCaseSingle(SearchPaginatedQueryModel params) {
        return repository.getProducts(params);
    }
}
