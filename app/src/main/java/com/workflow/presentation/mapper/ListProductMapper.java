package com.workflow.presentation.mapper;

import android.content.Context;

import com.workflow.data.model.ListModel;
import com.workflow.data.model.ProductModel;
import com.workflow.utils.WorkflowUtils;

/**
 * Created by Michael on 05/07/19.
 */

public class ListProductMapper extends InterModelMapper<ListModel, ProductModel> {
    Context context;

    public ListProductMapper(Context context) {
        this.context = context;
    }

    @Override
    public ListModel transform(ProductModel productModel) {
        String productCategory = WorkflowUtils.renderProductCategory(context, productModel.getProductCategoryId());
        return new ListModel(productModel.getId().toString(),
                String.format("%1s (%2s)", productModel.getArticleNo(), productCategory));
    }

    @Override
    public ListModel transform(ProductModel productModel, Integer attachId, String attachPos, String attachCreatedAt) {
        return null;
    }
}
