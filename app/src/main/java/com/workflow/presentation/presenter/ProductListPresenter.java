package com.workflow.presentation.presenter;

import com.workflow.data.model.ProductModel;

import java.util.List;

/**
 * Created by Michael on 18/06/19.
 */

public interface ProductListPresenter extends BasePresenter{
    void getProducts(String articleNo);
    void deleteProduct(List<ProductModel> param);

    Integer getCurrentPage();
    Integer getLimit();
    boolean isLoading();
    boolean isLastPage();

    void setIsLoading(boolean isLoading);
    void setIsLastPage(boolean isLastPage);
    void incrementCurrentPage();
    void setCurrentPage(int currentPage);
    void refreshData();
}
