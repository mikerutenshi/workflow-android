package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.workflow.R;
import com.workflow.data.model.WorkerModel;

/**
 * Created by Michael on 27/06/19.
 */

public class WorkerAdapter extends PaginationAdapter<WorkerModel
        , OnRecyclerObjectClickListener<WorkerModel>
        , BaseViewHolder<WorkerModel, OnRecyclerObjectClickListener<WorkerModel>>> {

    public WorkerAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseViewHolder<WorkerModel, OnRecyclerObjectClickListener<WorkerModel>> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            return new WorkerViewHolder(inflate(R.layout.item_worker, parent));
        } else if (viewType == LOADING) {
            return new LoadingObjectClickListenerViewHolder<>(inflate(R.layout.item_loading, parent));
        } else {
            return new WorkerViewHolder(inflate(R.layout.item_worker, parent));
        }
    }

    @Override
    public void initPlaceHolderItem() {
        placeholderItem = new WorkerModel();
    }
}
