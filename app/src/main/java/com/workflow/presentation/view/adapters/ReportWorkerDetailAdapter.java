package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.workflow.R;
import com.workflow.data.model.WorkDetailModel;

/**
 * Created by Michael on 25/07/19.
 */

public class ReportWorkerDetailAdapter extends PaginationAdapter<WorkDetailModel, OnRecyclerObjectClickListener, ReportWorkerDetailViewHolder> {

    public ReportWorkerDetailAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ReportWorkerDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReportWorkerDetailViewHolder(inflate(R.layout.item_report_work_detail, parent));
    }

    @Override
    public void initPlaceHolderItem() {

    }
}
