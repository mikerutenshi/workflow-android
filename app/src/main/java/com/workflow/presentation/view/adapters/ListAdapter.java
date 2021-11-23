package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.workflow.R;
import com.workflow.data.model.ListModel;

/**
 * Created by Michael on 03/07/19.
 */

public class ListAdapter extends PaginationAdapter<ListModel, OnRecyclerObjectClickListener<ListModel>, BaseViewHolder<ListModel, OnRecyclerObjectClickListener<ListModel>>> {

    public ListAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseViewHolder<ListModel, OnRecyclerObjectClickListener<ListModel>> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            return new ListViewHolder(inflate(R.layout.item_list, parent));
        } else if (viewType == LOADING) {
            return new LoadingObjectClickListenerViewHolder<ListModel>(inflate(R.layout.item_loading, parent));
        } else {
            return new ListViewHolder(inflate(R.layout.item_list, parent));
        }
    }

    @Override
    public void initPlaceHolderItem() {
        placeholderItem = new ListModel();
    }
}
