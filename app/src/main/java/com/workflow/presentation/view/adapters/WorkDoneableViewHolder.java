package com.workflow.presentation.view.adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;

import com.google.android.material.card.MaterialCardView;
import com.workflow.R;
import com.workflow.data.model.DoneWorkListModel;
import com.workflow.utils.DateUtils;
import com.workflow.utils.WorkflowUtils;

import butterknife.BindString;
import butterknife.BindView;

public class WorkDoneableViewHolder extends BaseViewHolder<DoneWorkListModel, OnRecyclerObjectClickListener<DoneWorkListModel>> {

    @BindView(R.id.layout_worker_work) MaterialCardView materialCardView;
    @BindView(R.id.tv_work_info_spk_field) AppCompatTextView tvSpkNo;
    @BindView(R.id.tv_work_info_date_field) AppCompatTextView tvSpkDate;
    @BindView(R.id.tv_work_info_article_field) AppCompatTextView tvArticleNo;
    @BindView(R.id.tv_work_info_category_field) AppCompatTextView tvCategory;
    @BindView(R.id.tv_work_info_quantity_field) AppCompatTextView tvQuantity;
    @BindView(R.id.gr_work_info_notes) Group grNotes;
    @BindView(R.id.iv_item_work_status) ImageView ivWorkStatus;

//    @BindView(R.id.tv_item_done_work_date_assigned) AppCompatTextView tvAssignedAt;

    @BindString(R.string.assigned_work_quantity) String strQuantity;
    @BindString(R.string.assigned_work_quantity_remaining) String strQuantityRemaining;
    @BindString(R.string.assigned_work_date_assigned) String strDateAssigned;
    @BindString(R.string.salary_report_detail_total_quantity) String strQuantitySimple;

    public WorkDoneableViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void onBind(DoneWorkListModel item, OnRecyclerObjectClickListener<DoneWorkListModel> listener) {
        grNotes.setVisibility(View.GONE);
        ivWorkStatus.setVisibility(View.GONE);
        materialCardView.setChecked(item.isChecked());
        tvSpkNo.setText(String.valueOf(item.getSpkNo()));
        tvSpkDate.setText(DateUtils.serverToClient(item.getCreatedAt(), DateUtils.TYPE_DATE));
        tvArticleNo.setText(item.getArticleNo());
        tvCategory.setText(item.getProductCategoryName());

        if (item.getQuantityRemaining().equals(item.getQuantityAssigned())) {
            tvQuantity.setText(String.format(strQuantitySimple, item.getQuantityRemaining()));
        } else {
            tvQuantity.setText(String.format(strQuantity, item.getQuantityRemaining(), item.getQuantityAssigned()));
        }
//        tvQuantityRemaining.setText(String.format(strQuantityRemaining, item.getQuantityRemaining()));
//        tvAssignedAt.setText(String.format(strDateAssigned, DateUtils.serverToClient(item.getAssignedAt(), DateUtils.TYPE_DATE_TIME)));

        materialCardView.setOnClickListener(v -> {
            listener.onItemClicked(getAdapterPosition(), item);
        });

    }
}
