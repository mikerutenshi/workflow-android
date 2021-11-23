package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.workflow.R;
import com.workflow.data.model.WorkerModel;
import com.workflow.presentation.view.activities.MainActivity;
import com.workflow.utils.WorkflowUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by Michael on 27/06/19.
 */

class WorkerViewHolder extends BaseViewHolder<WorkerModel, OnRecyclerObjectClickListener<WorkerModel>> {

    @BindView(R.id.tv_item_worker_name) TextView tvName;
    @BindView(R.id.tv_item_worker_position) TextView tvPosition;
    @BindView(R.id.parent_item_worker) MaterialCardView cardView;

    @BindString(R.string.worker_list_name) String strName;
    @BindString(R.string.worker_list_position) String strPosition;

    public WorkerViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void onBind(final WorkerModel item, final OnRecyclerObjectClickListener<WorkerModel> listener) {
        final Context context = cardView.getContext();
//        tvName.setText(String.format(strName, item.getName()));
        tvName.setText(item.getName());

        List<String> renderPositions = new ArrayList<>();
        for (String pos : item.getPositions()) {
            renderPositions.add(WorkflowUtils.getRenderedPosition(context, pos));
        }
//        tvPosition.setText(String.format(strPosition, WorkflowUtils.toCSV(renderPositions)));
        tvPosition.setText(WorkflowUtils.toCSV(renderPositions));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((MainActivity) context).isActionMode()) {
                    listener.onItemSelected(getAdapterPosition(), item);
                } else {
                    listener.onItemClicked(getAdapterPosition(), item);
                }
            }
        });
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(getAdapterPosition(), item);
                return true;
            }
        });

        //dynamic card bg color
        if (item.isSelected()) {
            cardView.setChecked(true);
        } else {
            cardView.setChecked(false);
        }
    }
}
