package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.workflow.R;
import com.workflow.data.model.DoneWorkListModel;

public class DoneWorkAdapter extends PaginationAdapter<DoneWorkListModel, OnRecyclerObjectClickListener<DoneWorkListModel>
        ,BaseViewHolder<DoneWorkListModel, OnRecyclerObjectClickListener<DoneWorkListModel>>>{
    public DoneWorkAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseViewHolder<DoneWorkListModel, OnRecyclerObjectClickListener<DoneWorkListModel>> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM:
            default:
                return new DoneWorkViewHolder(inflate(R.layout.item_worker_work_01, parent));
            case LOADING:
                return new LoadingObjectClickListenerViewHolder<>(inflate(R.layout.item_loading, parent));
        }
    }

    @Override
    public void initPlaceHolderItem() {
        placeholderItem = new DoneWorkListModel();
    }
}
