package com.workflow.presentation.view.adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.card.MaterialCardView;
import com.workflow.R;
import com.workflow.data.model.CurrentWorkDetailAssignedModel;
import com.workflow.utils.DateUtils;
import com.workflow.utils.WorkflowUtils;

import butterknife.BindString;
import butterknife.BindView;

public class CurrentWorkDetailAssignedViewHolder extends BaseViewHolder<CurrentWorkDetailAssignedModel, OnRecyclerObjectClickListener<CurrentWorkDetailAssignedModel>>{

    @BindView(R.id.layout_item_work_detail) MaterialCardView materialCardView;
    @BindView(R.id.tv_item_work_detail_by_position) AppCompatTextView tvByPosition;
    @BindView(R.id.tv_item_work_detail_by_name) AppCompatTextView tvByName;
    @BindView(R.id.tv_item_work_detail_quantity) AppCompatTextView tvQuantity;
    @BindView(R.id.tv_item_work_detail_date) AppCompatTextView tvDate;
    @BindView(R.id.iv_item_work_detail_status) ImageView ivCompleted;
    @BindView(R.id.tv_item_work_detail_date_label) AppCompatTextView tvDateLabel;

    @BindString(R.string.work_detail_assigned_to) String strAssignedTo;
    @BindString(R.string.assigned_work_date_assigned) String strAssignedAt;
    @BindString(R.string.salary_report_detail_total_quantity) String strQuantity;
    @BindString(R.string.label_date_assigned) String strDateAssigned;

    public CurrentWorkDetailAssignedViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void onBind(CurrentWorkDetailAssignedModel item, OnRecyclerObjectClickListener<CurrentWorkDetailAssignedModel> listener) {
        tvByName.setText(item.getName());
        tvByPosition.setText(WorkflowUtils.getRenderedPosition(materialCardView.getContext(), item.getPosition()));
        tvQuantity.setText(String.format(strQuantity, item.getQuantity()));
        tvDateLabel.setText(strDateAssigned);
        tvDate.setText(DateUtils.serverToClient(item.getAssignedAt(), DateUtils.TYPE_DATE_TIME));

        if (item.getSumDone() != null && item.getSumDone() >= item.getQuantity()) {
            ivCompleted.setImageResource(R.drawable.baseline_done_black_24);
        } else {
            ivCompleted.setImageResource(R.drawable.baseline_schedule_black_24);
        }
    }
}
