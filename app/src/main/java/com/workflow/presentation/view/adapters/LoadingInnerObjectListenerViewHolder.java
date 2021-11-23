package com.workflow.presentation.view.adapters;

import android.view.View;

/**
 * Created by Michael on 18/07/19.
 */

public class LoadingInnerObjectListenerViewHolder<T> extends BaseViewHolder<T, GenericRecyclerAdapter.OnRecyclerWithInnerObjectClickListener<T>> {

    public LoadingInnerObjectListenerViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void onBind(T item, GenericRecyclerAdapter.OnRecyclerWithInnerObjectClickListener<T> listener) {

    }
}
