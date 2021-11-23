package com.workflow.presentation.view.adapters;

import android.view.View;

/**
 * Created by Michael on 19/07/19.
 */

public class LoadingObjectClickListenerViewHolder<T> extends BaseViewHolder<T,OnRecyclerObjectClickListener<T>> {

    public LoadingObjectClickListenerViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void onBind(T item, OnRecyclerObjectClickListener<T> listener) {

    }
}
