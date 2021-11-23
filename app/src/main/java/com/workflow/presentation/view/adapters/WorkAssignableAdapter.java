package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.workflow.R;
import com.workflow.data.model.AssignedWorkListModel;

/**
 * Created by Michael on 06/07/19.
 */

public class WorkAssignableAdapter extends PaginationAdapter<AssignedWorkListModel, OnRecyclerObjectClickListener<AssignedWorkListModel>
        , BaseViewHolder<AssignedWorkListModel, OnRecyclerObjectClickListener<AssignedWorkListModel>>> {

    public WorkAssignableAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseViewHolder<AssignedWorkListModel, OnRecyclerObjectClickListener<AssignedWorkListModel>> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM:
            default:
                return new WorkAssignableViewHolder(inflate(R.layout.item_worker_work_01, parent));
            case LOADING:
                return new LoadingObjectClickListenerViewHolder<>(inflate(R.layout.item_loading, parent));
        }
    }

    @Override
    public void initPlaceHolderItem() {
        placeholderItem = new AssignedWorkListModel();
    }
}
