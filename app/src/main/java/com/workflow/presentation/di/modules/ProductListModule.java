package com.workflow.presentation.di.modules;

import android.content.Context;

import com.workflow.domain.interactor.product.DeleteProduct;
import com.workflow.domain.interactor.product.GetProducts;
import com.workflow.presentation.presenter.ProductListPresenter;
import com.workflow.presentation.presenter.ProductListPresenterImpl;
import com.workflow.presentation.view.ProductView;
import com.workflow.presentation.view.adapters.ProductAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael on 28/06/19.
 */

@Module(includes = { ProductModule.class, AdapterModule.class })
public class ProductListModule {

    private final ProductView productView;

    public ProductListModule(ProductView productView) {
        this.productView = productView;
    }

    @Provides
    ProductAdapter productAdapter(Context context) {
        return new ProductAdapter(context);
    }

    @Provides
    ProductListPresenter productListPresenter(GetProducts getProducts, DeleteProduct deleteProduct) {
        return new ProductListPresenterImpl(getProducts, deleteProduct, productView);
    }
}
