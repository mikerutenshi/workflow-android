package com.workflow.presentation.di.modules;

import android.content.Context;

import com.workflow.data.model.ListModel;
import com.workflow.data.repository.ProductDataRepository;
import com.workflow.domain.interactor.product.PostProduct;
import com.workflow.domain.interactor.product.UpdateProduct;
import com.workflow.domain.interactor.product_category.GetProductCategories;
import com.workflow.presentation.presenter.ProductCreatePresenter;
import com.workflow.presentation.presenter.ProductCreatePresenterImpl;
import com.workflow.presentation.view.CreateProductView;
import com.workflow.presentation.view.adapters.ActvArrayAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael on 28/06/19.
 */

@Module(includes = { ProductModule.class, ProductCategoryModule.class, ValidatorModule.class, ContextModule.class })
public class ProductCreateModule {

    private final CreateProductView view;

    public ProductCreateModule(CreateProductView view) {
        this.view = view;
    }

    @Provides
    PostProduct createProduct(ProductDataRepository repository) {
        return new PostProduct(repository);
    }

    @Provides
    ProductCreatePresenter productCreatePresenter(PostProduct postProduct, UpdateProduct updateProduct, GetProductCategories getProductCategories) {
        return new ProductCreatePresenterImpl(postProduct, updateProduct, getProductCategories, view);
    }

    @Provides ActvArrayAdapter actvArrayAdapter(Context context) {
        return new ActvArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item);
    }
}
