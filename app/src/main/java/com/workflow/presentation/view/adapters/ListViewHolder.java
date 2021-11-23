package com.workflow.presentation.view.adapters;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.workflow.R;
import com.workflow.data.model.ListModel;

import butterknife.BindColor;
import butterknife.BindView;
import timber.log.Timber;

/**
 * Created by Michael on 03/07/19.
 */

public class ListViewHolder extends BaseViewHolder<ListModel, OnRecyclerObjectClickListener<ListModel>> {

    @BindView(R.id.tv_item_list_name) TextView tvName;
    @BindView(R.id.cl_item_list_parent) ConstraintLayout clParent;
    @BindColor(R.color.primaryColor) int colorPrimary;
    @BindColor(android.R.color.tab_indicator_text) int colorTextPrimaryDark;

    public ListViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void onBind(final ListModel item, final OnRecyclerObjectClickListener<ListModel> listener) {
        tvName.setText(item.getName());
        clParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(getAdapterPosition(), item);
            }
        });

        if (item.isHighlighted()) {
            tvName.setTextColor(colorPrimary);
        } else {
            tvName.setTextColor(colorTextPrimaryDark);
        }
    }
}
