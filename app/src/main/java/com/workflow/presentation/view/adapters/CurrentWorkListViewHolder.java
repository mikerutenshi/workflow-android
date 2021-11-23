package com.workflow.presentation.view.adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.card.MaterialCardView;
import com.workflow.R;
import com.workflow.data.model.CurrentWorkListModel;
import com.workflow.utils.DateUtils;
import com.workflow.utils.Positions;

import butterknife.BindString;
import butterknife.BindView;

public class CurrentWorkListViewHolder extends BaseViewHolder<CurrentWorkListModel, OnRecyclerObjectClickListener<CurrentWorkListModel>> {
    private static final String STATUS_WIP = "STATUS_WIP";
    private static final String STATUS_DONE = "STATUS_DONE";

    @BindView(R.id.tv_item_current_work_draw_status_label) AppCompatTextView tvDrawStatus;
    @BindView(R.id.tv_item_current_work_lining_draw_status_label) AppCompatTextView tvLiningDrawStatus;
    @BindView(R.id.tv_item_current_work_sew_status_label) AppCompatTextView tvSewStatus;
    @BindView(R.id.tv_item_current_work_assemble_status_label) AppCompatTextView tvAssembleStatus;
    @BindView(R.id.tv_item_current_work_outsole_stitch_status_label) AppCompatTextView tvOutsoleStitchStatus;
    @BindView(R.id.tv_item_current_work_insole_stitch_status_label) AppCompatTextView tvInsoleStitchStatus;
    @BindView(R.id.tv_item_current_work_article_no) AppCompatTextView tvArticleNo;
    @BindView(R.id.tv_item_current_work_spk_no) AppCompatTextView tvSpkNo;
    @BindView(R.id.tv_item_current_work_created_at) AppCompatTextView tvCreatedAt;
    @BindView(R.id.tv_item_current_work_quantity) AppCompatTextView tvQuantity;
    @BindView(R.id.layout_item_current_work) MaterialCardView materialCardView;
    @BindView(R.id.iv_item_current_work_done) ImageView ivDone;

    @BindString(R.string.salary_report_detail_total_quantity) String strQuantity;

    private String drawStatus;
    private String liningDrawStatus;
    private String sewStatus;
    private String assembleStatus;
    private String outsoleStitchStatus;
    private String insoleStitchStatus;

    public CurrentWorkListViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void onBind(CurrentWorkListModel item, OnRecyclerObjectClickListener<CurrentWorkListModel> listener) {
        materialCardView.setChecked(item.isChecked());

        drawStatus = "";
        liningDrawStatus = "";
        sewStatus = "";
        assembleStatus = "";
        outsoleStitchStatus = "";
        insoleStitchStatus = "";

        tvArticleNo.setText(item.getArticleNo());
        tvSpkNo.setText(String.valueOf(item.getSpkNo()));
        tvCreatedAt.setText(DateUtils.serverToClient(item.getCreatedAt(), DateUtils.TYPE_DATE));
        tvQuantity.setText(String.format(strQuantity, item.getQuantity()));

        for (CurrentWorkListModel.WorkInProgress wip : item.getWip()) {
            switch (wip.getPosition()) {
                case Positions.DRAWER:
                    drawStatus = STATUS_WIP;
                    break;
                case Positions.LINING_DRAWER:
                    liningDrawStatus = STATUS_WIP;
                    break;
                case Positions.SEWER:
                    sewStatus = STATUS_WIP;
                    break;
                case Positions.ASSEMBLER:
                    assembleStatus = STATUS_WIP;
                    break;
                case Positions.SOLE_STITCHER:
                    outsoleStitchStatus = STATUS_WIP;
                    break;
                case Positions.INSOLE_STITCHER:
                    insoleStitchStatus = STATUS_WIP;
                    break;
            }
        }

        for (CurrentWorkListModel.WorkDone wd : item.getWorkDone()) {
            if (wd.getSum().equals(item.getQuantity())) {
                switch (wd.getPosition()) {
                    case Positions.DRAWER:
                        drawStatus = STATUS_DONE;
                        break;
                    case Positions.LINING_DRAWER:
                        liningDrawStatus = STATUS_DONE;
                        break;
                    case Positions.SEWER:
                        sewStatus = STATUS_DONE;
                        break;
                    case Positions.ASSEMBLER:
                        assembleStatus = STATUS_DONE;
                        break;
                    case Positions.SOLE_STITCHER:
                        outsoleStitchStatus = STATUS_DONE;
                        break;
                    case Positions.INSOLE_STITCHER:
                        insoleStitchStatus = STATUS_DONE;
                        break;
                }
            }
        }
//
//        Timber.d("article_no %s", item.getArticleNo());
//        Timber.d("draw_status %s", drawStatus);
//        Timber.d("lining_draw_status %s", liningDrawStatus);
//        Timber.d("sew_status %s", sewStatus);
//        Timber.d("assemble_status %s", assembleStatus);
//        Timber.d("outsole_stitch_status %s", outsoleStitchStatus);
//        Timber.d("insole_stitch_status %s", insoleStitchStatus);

        hideWorkStatuses();
        boolean isAllWorkDone = true;
        if (item.getDrawingCost() != 0 && !drawStatus.equals(STATUS_DONE)) isAllWorkDone = false;
        if (item.getLiningDrawingCost() != 0 && !liningDrawStatus.equals(STATUS_DONE)) isAllWorkDone = false;
        if (item.getSewingCost() != 0 && !sewStatus.equals(STATUS_DONE)) isAllWorkDone = false;
        if (item.getAssemblingCost() != 0 && !assembleStatus.equals(STATUS_DONE)) isAllWorkDone = false;
        if (item.getSoleStitchingCost() != 0 && !outsoleStitchStatus.equals(STATUS_DONE)) isAllWorkDone = false;
        if (item.getInsoleStitchingCost() != 0 && !insoleStitchStatus.equals(STATUS_DONE)) isAllWorkDone = false;

        if (isAllWorkDone) {
            ivDone.setVisibility(View.VISIBLE);
        } else {
            ivDone.setVisibility(View.GONE);
            renderWorkStatuses();
            setWorkStatusesAvailability(item);
        }

        materialCardView.setOnClickListener(v -> {
            listener.onItemClicked(getAdapterPosition(), item);
        });
        materialCardView.setOnLongClickListener(v -> {
            listener.onItemLongClick(getAdapterPosition(), item);
            return true;
        });
    }

    private void renderWorkStatuses() {
        if (drawStatus.equals(STATUS_WIP)) {
            tvDrawStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.baseline_schedule_black_18);
        } else if (drawStatus.equals(STATUS_DONE)) {
            tvDrawStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.baseline_done_black_18);
        } else {
            tvDrawStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        if (liningDrawStatus.equals(STATUS_WIP)) {
            tvLiningDrawStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.baseline_schedule_black_18);
        } else if (liningDrawStatus.equals(STATUS_DONE)) {
            tvLiningDrawStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.baseline_done_black_18);
        } else {
            tvLiningDrawStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        if (sewStatus.equals(STATUS_WIP)) {
            tvSewStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.baseline_schedule_black_18);
        } else if (sewStatus.equals(STATUS_DONE)) {
            tvSewStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.baseline_done_black_18);
        } else {
            tvSewStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        if (assembleStatus.equals(STATUS_WIP)) {
            tvAssembleStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.baseline_schedule_black_18);
        } else if (assembleStatus.equals(STATUS_DONE)) {
            tvAssembleStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.baseline_done_black_18);
        } else {
            tvAssembleStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        if (outsoleStitchStatus.equals(STATUS_WIP)) {
            tvOutsoleStitchStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.baseline_schedule_black_18);
        } else if (outsoleStitchStatus.equals(STATUS_DONE)) {
            tvOutsoleStitchStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.baseline_done_black_18);
        } else {
            tvOutsoleStitchStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        if (insoleStitchStatus.equals(STATUS_WIP)) {
            tvInsoleStitchStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.baseline_schedule_black_18);
        } else if (insoleStitchStatus.equals(STATUS_DONE)) {
            tvInsoleStitchStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.baseline_done_black_18);
        } else {
            tvInsoleStitchStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    private void setWorkStatusesAvailability(CurrentWorkListModel item) {
        if (item.getDrawingCost() != 0) tvDrawStatus.setVisibility(View.VISIBLE);
        if (item.getLiningDrawingCost() != 0) tvLiningDrawStatus.setVisibility(View.VISIBLE);
        if (item.getSewingCost() != 0) tvSewStatus.setVisibility(View.VISIBLE);
        if (item.getAssemblingCost() != 0) tvAssembleStatus.setVisibility(View.VISIBLE);
        if (item.getSoleStitchingCost() != 0) tvOutsoleStitchStatus.setVisibility(View.VISIBLE);
        if (item.getInsoleStitchingCost() != 0) tvInsoleStitchStatus.setVisibility(View.VISIBLE);
    }

    private void hideWorkStatuses() {
        tvDrawStatus.setVisibility(View.GONE);
        tvLiningDrawStatus.setVisibility(View.GONE);
        tvSewStatus.setVisibility(View.GONE);
        tvAssembleStatus.setVisibility(View.GONE);
        tvOutsoleStitchStatus.setVisibility(View.GONE);
        tvInsoleStitchStatus.setVisibility(View.GONE);
    }
}
