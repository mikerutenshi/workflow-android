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

public class DoneWorkViewHolder extends BaseViewHolder<DoneWorkListModel, OnRecyclerObjectClickListener<DoneWorkListModel>> {
    @BindView(R.id.layout_worker_work) MaterialCardView materialCardView;
    @BindView(R.id.tv_work_info_spk_field) AppCompatTextView tvSpkNo;
    @BindView(R.id.tv_work_info_date_field) AppCompatTextView tvSpkDate;
    @BindView(R.id.tv_work_info_article_field) AppCompatTextView tvArticleNo;
    @BindView(R.id.tv_work_info_category_field) AppCompatTextView tvCategory;
    @BindView(R.id.tv_work_info_quantity_field) AppCompatTextView tvQuantity;
//    @BindView(R.id.tv_item_done_work_quantity_remaining) AppCompatTextView tvQuantityRemaining;
//    @BindView(R.id.tv_item_done_work_date_assigned) AppCompatTextView tvDateAssigned;
    @BindView(R.id.tv_work_info_date_worked_field) AppCompatTextView tvDateDone;
    @BindView(R.id.tv_work_info_type_field) AppCompatTextView tvType;
    @BindView(R.id.tv_work_info_notes_field) AppCompatTextView tvNotes;
//    @BindView(R.id.view_item_done_work_bottom_margin) View viewBottomMargin;
    @BindView(R.id.iv_item_work_status) ImageView ivCompleted;
    @BindView(R.id.gr_work_info_type) Group grType;
    @BindView(R.id.gr_work_info_date_worked) Group grDateWorked;
    @BindView(R.id.tv_work_info_date_worked_label) AppCompatTextView tvDateWorkedLabel;

    @BindString(R.string.assigned_work_quantity) String strQuantity;
    @BindString(R.string.salary_report_detail_total_quantity) String strQuantitySimple;
//    @BindString(R.string.assigned_work_quantity_remaining) String strQuantityRemaining;
//    @BindString(R.string.assigned_work_date_assigned) String strDateAssigned;
//    @BindString(R.string.assigned_work_date_done) String strDateDone;
    @BindString(R.string.label_date_done) String strDateDoneLabel;

    public DoneWorkViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void onBind(DoneWorkListModel item, OnRecyclerObjectClickListener<DoneWorkListModel> listener) {
        grType.setVisibility(View.VISIBLE);
        grDateWorked.setVisibility(View.VISIBLE);
        tvDateWorkedLabel.setText(strDateDoneLabel);

        materialCardView.setChecked(item.isChecked());
        tvSpkNo.setText(String.valueOf(item.getSpkNo()));
        tvSpkDate.setText(DateUtils.serverToClient(item.getCreatedAt(), DateUtils.TYPE_DATE));
        tvArticleNo.setText(item.getArticleNo());
        tvCategory.setText(WorkflowUtils.transformProductCategory(itemView.getContext(), item.getProductCategoryName()));

        if (item.getQuantityDone().equals(item.getQuantityAssigned())) {
            tvQuantity.setText(String.format(strQuantitySimple, item.getQuantityDone()));
        } else {
            tvQuantity.setText(String.format(strQuantity, item.getQuantityDone(), item.getQuantityAssigned()));
        }
//        tvQuantityRemaining.setText(String.format(strQuantityRemaining, item.getQuantityRemaining()));
//        tvDateAssigned.setText(String.format(strDateAssigned, DateUtils.serverToClient(item.getAssignedAt(), DateUtils.TYPE_DATE_TIME)));
        tvDateDone.setText(DateUtils.serverToClient(item.getDoneAt(), DateUtils.TYPE_DATE_TIME));
        tvType.setText(WorkflowUtils.getRenderedPosition(materialCardView.getContext(), item.getPosition()));

        if (item.getNotes() != null && !item.getNotes().isEmpty()) {
            tvNotes.setText(item.getNotes());
//            tvNotes.setVisibility(View.VISIBLE);
//            viewBottomMargin.setVisibility(View.GONE);
        } else {
//            tvNotes.setVisibility(View.GONE);
//            viewBottomMargin.setVisibility(View.VISIBLE);
            tvNotes.setText(null);
        }

        if (item.getQuantityRemaining() == 0) {
            ivCompleted.setImageResource(R.drawable.baseline_done_black_24);
        } else {
            ivCompleted.setImageResource(R.drawable.baseline_schedule_black_24);
        }

        materialCardView.setOnClickListener(v -> {
            listener.onItemClicked(getAdapterPosition(), item);
        });
        materialCardView.setOnLongClickListener(v -> {
            listener.onItemLongClick(getAdapterPosition(), item);
            return true;
        });
    }
}
