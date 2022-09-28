package com.workflow.presentation.view.adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;

import com.google.android.material.card.MaterialCardView;
import com.workflow.R;
import com.workflow.data.model.AssignedWorkListModel;
import com.workflow.utils.DateUtils;
import com.workflow.utils.WorkflowUtils;

import butterknife.BindString;
import butterknife.BindView;


/**
 * Created by Michael on 06/07/19.
 */

class WorkAssignableViewHolder extends BaseViewHolder<AssignedWorkListModel, OnRecyclerObjectClickListener<AssignedWorkListModel>> {

    @BindView(R.id.layout_worker_work) MaterialCardView materialCardView;
    @BindView(R.id.tv_work_info_spk_field) AppCompatTextView tvSpkNo;
    @BindView(R.id.tv_work_info_date_field) AppCompatTextView tvSpkDate;
    @BindView(R.id.tv_work_info_article_field) AppCompatTextView tvArticleNo;
    @BindView(R.id.tv_work_info_category_field) AppCompatTextView tvCategory;
    @BindView(R.id.tv_work_info_quantity_field) AppCompatTextView tvQuantity;
    @BindView(R.id.gr_work_info_notes) Group grNotes;
    @BindView(R.id.iv_item_work_status) ImageView ivWorkStatus;
//    @BindView(R.id.tv_item_assigned_work_quantity_remaining) AppCompatTextView tvQuantityRemaining;

    @BindString(R.string.assigned_work_quantity_remaining) String strQuantityRemaining;
    @BindString(R.string.assigned_work_date_assigned) String strDateAssigned;
    @BindString(R.string.assigned_work_quantity) String strQuantity;
    @BindString(R.string.salary_report_detail_total_quantity) String strQuantitySimple;

    public WorkAssignableViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void onBind(AssignedWorkListModel item, OnRecyclerObjectClickListener<AssignedWorkListModel> listener) {
        grNotes.setVisibility(View.GONE);
        ivWorkStatus.setVisibility(View.GONE);
        materialCardView.setChecked(item.isChecked());
        tvSpkNo.setText(String.valueOf(item.getSpkNo()));
        tvSpkDate.setText(DateUtils.serverToClient(item.getCreatedAt(), DateUtils.TYPE_DATE));
        tvArticleNo.setText(item.getArticleNo());
        tvCategory.setText(WorkflowUtils.transformProductCategory(itemView.getContext(), item.getProductCategoryName()));
        if (item.getQuantityRemaining().equals(item.getQuantityInitial())) {
            tvQuantity.setText(String.format(strQuantitySimple, item.getQuantityRemaining()));
        } else {
            tvQuantity.setText(String.format(strQuantity, item.getQuantityRemaining(), item.getQuantityInitial()));
        }
//        tvQuantityRemaining.setText(String.format(strQuantityRemaining, item.getQuantityRemaining()));
//        tvDateAssigned.setText(String.format(strDateAssigned, WorkflowUtils.convertApiToIndonesianDateTime(item.getAssignedAt())));
//        tvType.setText(WorkflowUtils.getRenderedPosition(materialCardView.getContext(), item.getPosition()));

        materialCardView.setOnClickListener(v -> {
            listener.onItemClicked(getAdapterPosition(), item);
        });
    }
}
