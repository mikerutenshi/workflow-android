package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.workflow.R;
import com.workflow.data.model.CurrentWorkDetailAssignedModel;

public class CurrentWorkDetailAssignedAdapter extends PaginationAdapter<CurrentWorkDetailAssignedModel, OnRecyclerObjectClickListener<CurrentWorkDetailAssignedModel>
        ,BaseViewHolder<CurrentWorkDetailAssignedModel, OnRecyclerObjectClickListener<CurrentWorkDetailAssignedModel>>> {

    public CurrentWorkDetailAssignedAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseViewHolder<CurrentWorkDetailAssignedModel, OnRecyclerObjectClickListener<CurrentWorkDetailAssignedModel>> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM:
            default:
                return new CurrentWorkDetailAssignedViewHolder(inflate(R.layout.item_work_detail_01, parent));
            case LOADING:
                return new LoadingObjectClickListenerViewHolder<>(inflate(R.layout.item_loading, parent));
        }
    }

    @Override
    public void initPlaceHolderItem() {
        placeholderItem = new CurrentWorkDetailAssignedModel();
    }
}
