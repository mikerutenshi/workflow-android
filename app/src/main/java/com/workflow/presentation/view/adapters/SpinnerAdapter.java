package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.workflow.R;
import com.workflow.data.model.ListModel;

public class SpinnerAdapter extends GenericRecyclerAdapter<ListModel, OnRecyclerObjectClickListener<ListModel>, ListViewHolder> {

    public SpinnerAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(inflate(R.layout.item_list, parent));
    }
}
