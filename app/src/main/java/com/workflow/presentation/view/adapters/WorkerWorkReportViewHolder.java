package com.workflow.presentation.view.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.workflow.R;
import com.workflow.data.model.ReportListModel;
import com.workflow.utils.WorkflowUtils;

import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by Michael on 10/07/19.
 */

public class WorkerWorkReportViewHolder extends BaseViewHolder<ReportListModel, OnRecyclerObjectClickListener<ReportListModel>> {

    @BindView(R.id.tv_item_worker_work_report_name) TextView tvName;
    @BindView(R.id.tv_item_worker_work_report_total_cost) TextView tvCost;
    @BindView(R.id.tv_item_worker_work_report_total_quantity) TextView tvQty;
    @BindView(R.id.parent_item_worker_work_report) CardView cvParent;
    @BindView(R.id.tv_item_worker_work_report_position) AppCompatTextView tvPosition;
    @BindString(R.string.report_item_name) String strName;
    @BindString(R.string.report_item_total_cost) String strCost;
    @BindString(R.string.report_item_total_qty) String strQty;
    @BindString(R.string.worker_list_position) String strPosition;

    public WorkerWorkReportViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void onBind(final ReportListModel item, final OnRecyclerObjectClickListener<ReportListModel> listener) {
        tvName.setText(String.format(strName, item.getName()));
        tvCost.setText(String.format(strCost, WorkflowUtils.convertRupiah(item.getTotalCost())));
        tvQty.setText(String.format(strQty, item.getTotalQty()));
        tvPosition.setText(String.format(strPosition
                , WorkflowUtils.getRenderedPosition(tvPosition.getContext(), item.getPosition())));
        cvParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(getAdapterPosition(), item);
            }
        });
    }
}
