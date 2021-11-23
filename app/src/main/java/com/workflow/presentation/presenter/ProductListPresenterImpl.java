package com.workflow.presentation.presenter;

import com.workflow.data.model.GenericItemPaginationModel;
import com.workflow.data.model.SearchPaginatedQueryModel;
import com.workflow.data.model.ProductModel;
import com.workflow.domain.interactor.product.DeleteProduct;
import com.workflow.domain.interactor.product.GetProducts;
import com.workflow.presentation.view.ProductView;
import com.workflow.utils.WorkflowUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 18/06/19.
 */

public class ProductListPresenterImpl implements ProductListPresenter {

    private final GetProducts getProducts;
    private final DeleteProduct deleteProduct;
    private final ProductView view;
    private int currentPage = 1;
    private int limit = 10;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    public ProductListPresenterImpl(GetProducts getProducts, DeleteProduct deleteProduct, ProductView view) {
        this.getProducts = getProducts;
        this.deleteProduct = deleteProduct;
        this.view = view;
    }

    @Override
    public void getProducts(String articleNo) {
        if (view.isConnected()) {
            SearchPaginatedQueryModel param = new SearchPaginatedQueryModel(articleNo, limit, currentPage);
            getProducts.execute(new GenericObserver<GenericItemPaginationModel<List<ProductModel>>>(view) {
                @Override
                public void onSuccess(GenericItemPaginationModel<List<ProductModel>> model) {
                    super.onSuccess(model);
                    if (model.getItems().size() > 0) {
                        view.renderItems(model, currentPage);
                    } else {
                        view.showDataEmpty();
                    }
                }
            }, param);
        }
    }

    @Override
    public void deleteProduct(final List<ProductModel> param) {
        if (view.isConnected()) {
            view.showLoading();
            List<Integer> idsToDelete = new ArrayList<>();
            for (ProductModel productModel : param) {
                idsToDelete.add(productModel.getId());
            }
            deleteProduct.execute(new GenericObserver<String>(view) {
                @Override
                public void onSuccess(String s) {
                    super.onSuccess(s);
//                    view.adapterDeleteItem(param);
                    refreshData();
                    view.showSnackBar(WorkflowUtils.SNACK_BAR_SUCCESS, s);
                }
            }, idsToDelete);
        }
    }

    @Override
    public Integer getCurrentPage() {
        return currentPage;
    }

    @Override
    public Integer getLimit() {
        return limit;
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public boolean isLastPage() {
        return isLastPage;
    }

    @Override
    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    @Override
    public void incrementCurrentPage() {
        currentPage ++;
    }

    @Override
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public void refreshData() {
        currentPage = 1;
        if (view.isConnected()) {
            view.showLoading();
            getProducts(null);
        }
    }

    @Override
    public void resume() {
        currentPage = 1;
        if (view.isConnected()) {
            view.showLoading();
            getProducts(null);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getProducts.dispose();
    }
}
