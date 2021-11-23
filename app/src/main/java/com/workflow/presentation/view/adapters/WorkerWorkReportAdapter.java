package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.workflow.R;
import com.workflow.data.model.ReportListModel;

/**
 * Created by Michael on 10/07/19.
 */

public class WorkerWorkReportAdapter extends GenericRecyclerAdapter<ReportListModel, OnRecyclerObjectClickListener<ReportListModel>, WorkerWorkReportViewHolder> {

    public WorkerWorkReportAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public WorkerWorkReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorkerWorkReportViewHolder(inflate(R.layout.item_worker_work_report, parent));
    }
}
