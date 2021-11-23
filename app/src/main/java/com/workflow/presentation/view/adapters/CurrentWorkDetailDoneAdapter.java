package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.workflow.R;
import com.workflow.data.model.CurrentWorkDetailDoneModel;

public class CurrentWorkDetailDoneAdapter extends PaginationAdapter<CurrentWorkDetailDoneModel, OnRecyclerObjectClickListener<CurrentWorkDetailDoneModel>
        ,BaseViewHolder<CurrentWorkDetailDoneModel, OnRecyclerObjectClickListener<CurrentWorkDetailDoneModel>>>{

    public CurrentWorkDetailDoneAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseViewHolder<CurrentWorkDetailDoneModel, OnRecyclerObjectClickListener<CurrentWorkDetailDoneModel>> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM:
            default:
                return new CurrentWorkDetailDoneViewHolder(inflate(R.layout.item_work_detail_01, parent));
            case LOADING:
                return new LoadingObjectClickListenerViewHolder<>(inflate(R.layout.item_loading, parent));
        }
    }

    @Override
    public void initPlaceHolderItem() {
        placeholderItem = new CurrentWorkDetailDoneModel();
    }
}
