package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.workflow.R;
import com.workflow.data.model.WorkerWorkModel;

public class WorkerWorkAdapter extends PaginationAdapter<WorkerWorkModel, OnRecyclerObjectClickListener<WorkerWorkModel>
        ,BaseViewHolder<WorkerWorkModel, OnRecyclerObjectClickListener<WorkerWorkModel>>> {

    public WorkerWorkAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseViewHolder<WorkerWorkModel, OnRecyclerObjectClickListener<WorkerWorkModel>> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            return new WorkerWorkViewHolder(inflate(R.layout.item_worker_work, parent));
        } else if (viewType == LOADING) {
            return new LoadingObjectClickListenerViewHolder<>(inflate(R.layout.item_loading, parent));
        } else {
            return new WorkerWorkViewHolder(inflate(R.layout.item_worker_work, parent));
        }
    }

    @Override
    public void initPlaceHolderItem() {
        placeholderItem = new WorkerWorkModel();
    }
}
