package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.workflow.R;
import com.workflow.data.model.CurrentWorkListModel;

public class CurrentWorkListAdapter extends PaginationAdapter<CurrentWorkListModel, OnRecyclerObjectClickListener<CurrentWorkListModel>
        ,BaseViewHolder<CurrentWorkListModel, OnRecyclerObjectClickListener<CurrentWorkListModel>>> {

    public CurrentWorkListAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseViewHolder<CurrentWorkListModel, OnRecyclerObjectClickListener<CurrentWorkListModel>> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM:
            default:
//                return new CurrentWorkListViewHolder(inflate(R.layout.item_current_work_list, parent));
                return new CurrentWorkListViewHolder01(inflate(R.layout.item_current_work_list_01, parent));
            case LOADING:
                return new LoadingObjectClickListenerViewHolder<>(inflate(R.layout.item_loading, parent));
        }
    }

    @Override
    public void initPlaceHolderItem() {
        placeholderItem = new CurrentWorkListModel();
    }
}
