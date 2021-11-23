package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.workflow.R;
import com.workflow.data.model.SalaryReportListModel;

public class SalaryReportListAdapter extends PaginationAdapter<SalaryReportListModel.Item, OnRecyclerObjectClickListener<SalaryReportListModel.Item>
        ,BaseViewHolder<SalaryReportListModel.Item, OnRecyclerObjectClickListener<SalaryReportListModel.Item>>> {

    public SalaryReportListAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseViewHolder<SalaryReportListModel.Item, OnRecyclerObjectClickListener<SalaryReportListModel.Item>> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM:
            default:
                return new SalaryReportListViewHolder(inflate(R.layout.item_salary_report, parent));
            case LOADING:
                return new LoadingObjectClickListenerViewHolder<>(inflate(R.layout.item_loading, parent));
        }
    }

    @Override
    public void initPlaceHolderItem() {
        placeholderItem = new SalaryReportListModel.Item();
    }
}
