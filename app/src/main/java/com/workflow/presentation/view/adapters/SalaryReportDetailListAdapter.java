package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.workflow.R;
import com.workflow.data.model.SalaryReportDetailListModel;

public class SalaryReportDetailListAdapter extends PaginationAdapter<SalaryReportDetailListModel.Item, OnRecyclerObjectClickListener<SalaryReportDetailListModel.Item>
        ,BaseViewHolder<SalaryReportDetailListModel.Item, OnRecyclerObjectClickListener<SalaryReportDetailListModel.Item>>> {

    public SalaryReportDetailListAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseViewHolder<SalaryReportDetailListModel.Item, OnRecyclerObjectClickListener<SalaryReportDetailListModel.Item>> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM:
            default:
                return new SalaryReportDetailListViewHolder(inflate(R.layout.item_salary_report_detail, parent));
            case LOADING:
                return new LoadingObjectClickListenerViewHolder<>(inflate(R.layout.item_loading, parent));
        }
    }

    @Override
    public void initPlaceHolderItem() {
        placeholderItem = new SalaryReportDetailListModel.Item();
    }
}
