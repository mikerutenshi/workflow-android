package com.workflow.presentation.mapper;

import com.workflow.data.model.ListModel;
import com.workflow.data.model.ProductModel;

/**
 * Created by Michael on 05/07/19.
 */

public class ListProductMapper extends InterModelMapper<ListModel, ProductModel> {
    @Override
    public ListModel transform(ProductModel productModel) {
        return new ListModel(productModel.getId().toString(),
                String.format("%1s (%2s)", productModel.getArticleNo(), productModel.getProductCategoryName()));
    }

    @Override
    public ListModel transform(ProductModel productModel, Integer attachId, String attachPos, String attachCreatedAt) {
        return null;
    }
}
