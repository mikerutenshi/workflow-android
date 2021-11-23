package com.workflow.presentation.view.adapters;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.card.MaterialCardView;
import com.workflow.R;
import com.workflow.data.model.SalaryReportListModel;
import com.workflow.utils.WorkflowUtils;

import butterknife.BindString;
import butterknife.BindView;

class SalaryReportListViewHolder extends BaseViewHolder<SalaryReportListModel.Item, OnRecyclerObjectClickListener<SalaryReportListModel.Item>> {

    @BindView(R.id.layout_item_salary_report) MaterialCardView materialCardView;
    @BindView(R.id.tv_item_salary_report_name) AppCompatTextView tvName;
    @BindView(R.id.tv_item_salary_report_quantity) AppCompatTextView tvQuantity;
    @BindView(R.id.tv_item_salary_report_cost) AppCompatTextView tvCost;
    @BindString(R.string.report_total_qty) String strQuantity;

    SalaryReportListViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void onBind(SalaryReportListModel.Item item, OnRecyclerObjectClickListener<SalaryReportListModel.Item> listener) {
        tvName.setText(item.getName());
        tvQuantity.setText(String.format(strQuantity, item.getTotalQuantity()));
        tvCost.setText(WorkflowUtils.convertRupiah(item.getTotalCost()));
        materialCardView.setOnClickListener(v -> listener.onItemClicked(getAdapterPosition(), item));
    }
}
