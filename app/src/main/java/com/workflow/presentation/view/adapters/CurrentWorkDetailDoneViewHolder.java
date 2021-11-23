package com.workflow.presentation.view.adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.card.MaterialCardView;
import com.workflow.R;
import com.workflow.data.model.CurrentWorkDetailDoneModel;
import com.workflow.utils.DateUtils;
import com.workflow.utils.WorkflowUtils;

import butterknife.BindString;
import butterknife.BindView;

public class CurrentWorkDetailDoneViewHolder extends BaseViewHolder<CurrentWorkDetailDoneModel, OnRecyclerObjectClickListener<CurrentWorkDetailDoneModel>> {

    @BindView(R.id.layout_item_work_detail) MaterialCardView materialCardView;
    @BindView(R.id.tv_item_work_detail_by_position) AppCompatTextView tvByPosition;
    @BindView(R.id.tv_item_work_detail_by_name) AppCompatTextView tvByName;
    @BindView(R.id.tv_item_work_detail_quantity) AppCompatTextView tvQuantity;
    @BindView(R.id.tv_item_work_detail_date) AppCompatTextView tvDate;
    @BindView(R.id.iv_item_work_detail_status) ImageView ivCompleted;
    @BindView(R.id.tv_item_work_detail_date_label) AppCompatTextView tvDateLabel;

    @BindString(R.string.work_detail_done_by) String strDoneBy;
    @BindString(R.string.assigned_work_date_done) String strDoneAt;
    @BindString(R.string.salary_report_detail_total_quantity) String strQuantity;
    @BindString(R.string.label_date_done) String strDateDone;

    public CurrentWorkDetailDoneViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void onBind(CurrentWorkDetailDoneModel item, OnRecyclerObjectClickListener<CurrentWorkDetailDoneModel> listener) {
        tvByName.setText(item.getName());
        tvByPosition.setText(WorkflowUtils.getRenderedPosition(materialCardView.getContext(), item.getPosition()));
        tvQuantity.setText(String.format(strQuantity, item.getQuantity()));
        tvDateLabel.setText(strDateDone);
        tvDate.setText(DateUtils.serverToClient(item.getDoneAt(), DateUtils.TYPE_DATE_TIME));

        if (item.getQuantityRemaining() == 0) {
            ivCompleted.setVisibility(View.VISIBLE);
        } else {
            ivCompleted.setVisibility(View.GONE);
        }
    }
}
