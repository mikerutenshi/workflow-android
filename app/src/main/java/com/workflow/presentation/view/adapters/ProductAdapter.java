package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.workflow.R;
import com.workflow.data.model.ProductModel;

/**
 * Created by Michael on 11/06/19.
 */

public class ProductAdapter extends PaginationAdapter<ProductModel, OnRecyclerObjectClickListener<ProductModel>, BaseViewHolder<ProductModel, OnRecyclerObjectClickListener<ProductModel>>> {

    public ProductAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseViewHolder<ProductModel, OnRecyclerObjectClickListener<ProductModel>> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            return new ProductViewHolder(inflate(R.layout.item_product_01, parent));
        } else if (viewType == LOADING) {
            return new LoadingObjectClickListenerViewHolder<>(inflate(R.layout.item_loading, parent));
        } else {
            return new ProductViewHolder(inflate(R.layout.item_product, parent));
        }
    }

    @Override
    public void initPlaceHolderItem() {
        placeholderItem = new ProductModel();
    }
}
