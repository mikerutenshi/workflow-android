package com.workflow.presentation.view.adapters;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

/**
 * Created by Michael on 11/06/19.
 */

public abstract class BaseViewHolder<T, L extends BaseRecyclerListener> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    abstract void onBind(T item, @Nullable L listener);
}
