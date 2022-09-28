package com.workflow.presentation.view.adapters;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;

import com.google.android.material.card.MaterialCardView;
import com.workflow.R;
import com.workflow.data.model.SalaryReportDetailListModel;
import com.workflow.utils.DateUtils;
import com.workflow.utils.WorkflowUtils;

import butterknife.BindString;
import butterknife.BindView;

public class SalaryReportDetailListViewHolder extends BaseViewHolder<SalaryReportDetailListModel.Item, OnRecyclerObjectClickListener<SalaryReportDetailListModel.Item>> {

    @BindView(R.id.layout_item_salary_report_detail) MaterialCardView materialCardView;
    @BindView(R.id.tv_work_info_spk_field) AppCompatTextView tvSpkNo;
    @BindView(R.id.tv_work_info_date_field) AppCompatTextView tvSpkDate;
    @BindView(R.id.tv_work_info_article_field) AppCompatTextView tvArticle;
    @BindView(R.id.tv_work_info_category_field) AppCompatTextView tvCategory;
    @BindView(R.id.tv_work_info_type_field) AppCompatTextView tvWorkType;
    @BindView(R.id.tv_work_info_date_worked_field) AppCompatTextView tvDoneAt;
    @BindView(R.id.tv_work_info_date_worked_label) AppCompatTextView tvDoneAtLabel;
    @BindView(R.id.gr_work_info_type) Group grType;
    @BindView(R.id.gr_work_info_notes) Group grNotes;
    @BindView(R.id.gr_work_info_date_worked) Group grDateWorked;
    @BindView(R.id.tv_work_info_quantity_label) AppCompatTextView tvQuantityLabel;
    @BindView(R.id.tv_work_info_quantity_field) AppCompatTextView tvQuantityField;
//    @BindView(R.id.tv_item_salary_report_detail_unit_cost) AppCompatTextView tvUnitCost;
//    @BindView(R.id.tv_item_salary_report_detail_quantity) AppCompatTextView tvQuantity;
//    @BindView(R.id.tv_item_salary_report_detail_total_cost) AppCompatTextView tvTotalCost;

    @BindString(R.string.report_total_formulae) String strQuantity;
    @BindString(R.string.report_subtotal_label) String strTotal;
    @BindString(R.string.salary_report_detail_sub_total_cost) String strTotalCost;
    @BindString(R.string.salary_report_detail_unit_cost) String strUnitCost;
    @BindString(R.string.label_date_done) String strDateDone;

    public SalaryReportDetailListViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void onBind(SalaryReportDetailListModel.Item item, OnRecyclerObjectClickListener<SalaryReportDetailListModel.Item> listener) {
        grType.setVisibility(View.VISIBLE);
        grNotes.setVisibility(View.GONE);
        grDateWorked.setVisibility(View.VISIBLE);
        tvDoneAtLabel.setText(strDateDone);
        tvSpkNo.setText(String.valueOf(item.getSpkNo()));
        tvSpkDate.setText(DateUtils.serverToClient(item.getCreatedAt(), DateUtils.TYPE_DATE));
        tvArticle.setText(item.getArticleNo());
        tvCategory.setText(WorkflowUtils.transformProductCategory(itemView.getContext(), item.getProductCategoryName()));
        tvWorkType.setText(WorkflowUtils.getRenderedPosition(materialCardView.getContext(), item.getPosition()));
        tvQuantityLabel.setText(strTotal);
        tvQuantityField.setText(String.format(strQuantity, item.getQuantity(),
                WorkflowUtils.convertRupiah(item.getUnitCost()), WorkflowUtils.convertRupiah(item.getSubTotalCost())));
        tvDoneAt.setText(DateUtils.serverToClient(item.getDoneAt(), DateUtils.TYPE_DATE_TIME));
    }
}
