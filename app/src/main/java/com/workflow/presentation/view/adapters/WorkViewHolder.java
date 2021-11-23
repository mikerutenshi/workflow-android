package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.material.card.MaterialCardView;
import com.workflow.R;
import com.workflow.data.model.WorkModel;
import com.workflow.presentation.view.activities.MainActivity;
import com.workflow.presentation.view.activities.WorkerDetailActivity;
import com.workflow.utils.WorkflowUtils;

import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by Michael on 27/06/19.
 */

class WorkViewHolder extends BaseViewHolder<WorkModel, OnRecyclerObjectClickListener<WorkModel>> {

    @BindView(R.id.tv_item_work_spk_no) TextView tvSpkNo;
    @BindView(R.id.tv_item_work_article_no) TextView tvArticleNo;
    @BindView(R.id.tv_item_work_quantity) TextView tvQty;
    @BindView(R.id.cv_item_work) MaterialCardView cardView;
    @BindView(R.id.tv_item_work_drawing_status) AppCompatTextView tvDrawStats;
    @BindView(R.id.tv_item_work_sewing_status) AppCompatTextView tvSewStats;
    @BindView(R.id.tv_item_work_assembling_status) AppCompatTextView tvAssembleStats;
    @BindView(R.id.tv_item_work_sole_stitching_status) AppCompatTextView tvSoleStitchStats;
    @BindView(R.id.tv_item_work_lining_drawing_status) AppCompatTextView tvLiningDrawingStats;
    @BindView(R.id.tv_item_work_insole_stitching_status) AppCompatTextView tvInsoleStitchingStats;
    @BindView(R.id.tv_item_work_date_created) AppCompatTextView tvDateCreated;
    @BindView(R.id.tv_item_work_date_updated) AppCompatTextView tvDateUpdated;

    @BindString(R.string.work_list_spk_no) String strSpkNo;
    @BindString(R.string.product_list_article_no) String strArticleNo;
    @BindString(R.string.work_list_quantity) String strQty;
    @BindString(R.string.work_list_date_created) String strDateCreated;
    @BindString(R.string.work_list_date_updated) String strDateUpdated;

    public WorkViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void onBind(final WorkModel item, final OnRecyclerObjectClickListener<WorkModel> listener) {
        tvSpkNo.setText(String.format(strSpkNo, item.getSpkNo()));
        tvArticleNo.setText(String.format(strArticleNo, item.getArticleNo()));
        tvQty.setText(String.format(strQty, item.getQty()));

        final Context context = cardView.getContext();
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof MainActivity) {
                    if (((MainActivity) context).isActionMode()) {
                        listener.onItemSelected(getAdapterPosition(), item);
                    } else {
                        listener.onItemClicked(getAdapterPosition(), item);
                    }
                } else if (context instanceof WorkerDetailActivity) {
                    if (((WorkerDetailActivity) context).isActionMode()) {
//                        listener.onItemSelected(getAdapterPosition(), item);
                    } else {
                        listener.onItemClicked(getAdapterPosition(), item);
                    }
                }
            }
        });

        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(getAdapterPosition(), item);
                return true;
            }
        });

        //dynamic card bg color
        if (item.isSelected()) {
            cardView.setChecked(true);
        } else {
            cardView.setChecked(false);
        }

        // show work status
//        int textPrimaryDark = ContextCompat.getColor(context, R.color.material_text_primary_dark);
        int textPrimaryDark = Color.GRAY;
        tvDrawStats.setTextColor(item.isDrawn() ? textPrimaryDark : Color.LTGRAY);
        tvSewStats.setTextColor(item.isSewn() ? textPrimaryDark : Color.LTGRAY);
        tvAssembleStats.setTextColor(item.isAssembled() ? textPrimaryDark : Color.LTGRAY);
        tvSoleStitchStats.setTextColor(item.isSoleStitched() ? textPrimaryDark : Color.LTGRAY);
        tvLiningDrawingStats.setTextColor(item.isLiningDrawn() ? textPrimaryDark : Color.LTGRAY);
        tvInsoleStitchingStats.setTextColor(item.isInsoleStitched() ? textPrimaryDark : Color.LTGRAY);
        tvSoleStitchStats.setVisibility(item.getSoleStitchingCost() == 0 ? View.GONE : View.VISIBLE);
        tvLiningDrawingStats.setVisibility(item.getLiningDrawingCost() == 0 ? View.GONE : View.VISIBLE);
        tvInsoleStitchingStats.setVisibility(item.getInsoleStitchingCost() == 0 ? View.GONE : View.VISIBLE);

        // show important dates
        tvDateCreated.setText(String.format(strDateCreated
                , WorkflowUtils.convertApiToIndonesianDate(item.getCreatedAt())));
        if (item.getUpdatedAt() == null) {
            tvDateUpdated.setVisibility(View.GONE);
        } else {
            tvDateUpdated.setText(String.format(strDateUpdated
                    , WorkflowUtils.convertApiToIndonesianDateTime(item.getUpdatedAt())));
            tvDateUpdated.setVisibility(View.VISIBLE);
        }
    }
}
