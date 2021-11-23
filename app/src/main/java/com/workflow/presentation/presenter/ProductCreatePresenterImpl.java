package com.workflow.presentation.presenter;

import com.workflow.data.model.ListModel;
import com.workflow.data.model.ProductModel;
import com.workflow.domain.interactor.product.PostProduct;
import com.workflow.domain.interactor.product.UpdateProduct;
import com.workflow.domain.interactor.product_category.GetProductCategories;
import com.workflow.presentation.view.CreateProductView;
import com.workflow.utils.WorkflowUtils;

import java.util.List;

/**
 * Created by Michael on 21/06/19.
 */

public class ProductCreatePresenterImpl implements ProductCreatePresenter {

    private final PostProduct postProduct;
    private final UpdateProduct updateProduct;
    private final GetProductCategories getProductCategories;
    private final CreateProductView view;

    public ProductCreatePresenterImpl(PostProduct postProduct, UpdateProduct updateProduct, GetProductCategories getProductCategories, CreateProductView view) {
        this.postProduct = postProduct;
        this.updateProduct = updateProduct;
        this.getProductCategories = getProductCategories;
        this.view = view;
    }

    @Override
    public void resume() {
        getProductCategories();
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        postProduct.dispose();
    }

    @Override
    public void postProduct(ProductModel param) {
        if (view.isConnected()) {
            view.showLoading();
            postProduct.execute(new GenericObserver<String>(view) {
                @Override
                public void onSuccess(String s) {
                    super.onSuccess(s);
                    view.showSnackThenClearForm(WorkflowUtils.SNACK_BAR_SUCCESS, s);
                }
            }, param);
        }
    }

    @Override
    public void updateProduct(ProductModel productModel) {
        if (view.isConnected()) {
            view.showLoading();
            updateProduct.execute(new GenericObserver<String>(view) {
                @Override
                public void onSuccess(String s) {
                    super.onSuccess(s);
                    view.showSnackBar(WorkflowUtils.SNACK_BAR_SUCCESS, s);
                }
            }, productModel);
        }
    }

    @Override
    public void getProductCategories() {
        if (view.getAdapterSize() < 1) {
            if (view.isConnected()) {
                view.showLoading();
                getProductCategories.execute(new GenericObserver<List<ListModel>>(view) {
                    @Override
                    public void onSuccess(List<ListModel> listModels) {
                        super.onSuccess(listModels);
                        view.renderProductCategories(listModels);
                    }
                }, null);
            }
        }
    }
}
