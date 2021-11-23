package com.workflow.presentation.view.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.workflow.R;
import com.workflow.data.model.WorkDetailModel;
import com.workflow.utils.WorkflowUtils;

import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by Michael on 25/07/19.
 */

public class ReportWorkerDetailViewHolder extends BaseViewHolder<WorkDetailModel, OnRecyclerObjectClickListener> {

    @BindView(R.id.tv_item_report_work_detail_spk_no) TextView tvSpkNo;
    @BindView(R.id.tv_item_report_work_detail_article_no) TextView tvArticleNo;
    @BindView(R.id.tv_item_report_work_detail_unit_cost) TextView tvUnitCost;
    @BindView(R.id.tv_item_report_work_detail_quantity) TextView tvQty;
    @BindView(R.id.tv_item_report_work_detail_total_cost) TextView tvTotalCost;
    @BindView(R.id.tv_item_report_work_detail_work_type) TextView tvWorkType;
    @BindView(R.id.tv_item_report_work_detail_date) AppCompatTextView tvDate;

    @BindString(R.string.product_list_article_no) String strArticleNo;
    @BindString(R.string.work_list_spk_no) String strSpkNo;
    @BindString(R.string.work_list_quantity) String strQty;
    @BindString(R.string.report_item_total_cost) String strTotalCost;
    @BindString(R.string.report_detail_unit_price) String strUnitPrice;
    @BindString(R.string.work_list_work_type) String strWorkType;
    @BindString(R.string.work_list_date_done) String strDate;

    public ReportWorkerDetailViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void onBind(WorkDetailModel item, OnRecyclerObjectClickListener listener) {
        tvSpkNo.setText(String.format(strSpkNo, item.getSpkNo()));
        tvArticleNo.setText(String.format(strArticleNo, item.getArticleNo()));
        tvUnitCost.setText(String.format(strUnitPrice, WorkflowUtils.convertRupiah(item.getUnitCost())));
        tvQty.setText(String.format(strQty, item.getQty()));
        tvTotalCost.setText(String.format(strTotalCost, WorkflowUtils.convertRupiah(item.getTotalCost())));
        tvWorkType.setText(String.format(strWorkType
                , WorkflowUtils.getRenderedPosition(tvWorkType.getContext(), item.getPosition())));
        tvDate.setText(String.format(strDate, WorkflowUtils.convertApiToIndonesianDateTime(item.getCreatedAt())));
    }
}
