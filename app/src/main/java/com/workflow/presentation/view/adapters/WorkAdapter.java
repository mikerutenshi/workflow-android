package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.workflow.R;
import com.workflow.data.model.WorkModel;

/**
 * Created by Michael on 27/06/19.
 */

public class WorkAdapter extends PaginationAdapter<WorkModel, OnRecyclerObjectClickListener<WorkModel>, BaseViewHolder<WorkModel, OnRecyclerObjectClickListener<WorkModel>>> {

    public WorkAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseViewHolder<WorkModel, OnRecyclerObjectClickListener<WorkModel>> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            return new WorkViewHolder(inflate(R.layout.item_work, parent));
        } else if (viewType == LOADING) {
            return new LoadingObjectClickListenerViewHolder<>(inflate(R.layout.item_loading, parent));
        } else {
            return new WorkViewHolder(inflate(R.layout.item_work, parent));
        }
    }

    @Override
    public void initPlaceHolderItem() {
        placeholderItem = new WorkModel();
    }
}
