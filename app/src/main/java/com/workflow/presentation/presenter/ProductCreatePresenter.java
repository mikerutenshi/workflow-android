package com.workflow.presentation.presenter;

import com.workflow.data.model.ListModel;
import com.workflow.data.model.ProductModel;

import java.util.List;

/**
 * Created by Michael on 21/06/19.
 */

public interface ProductCreatePresenter extends BasePresenter {
    void postProduct(ProductModel param);
    void updateProduct(ProductModel productModel);
    void getProductCategories();
}
