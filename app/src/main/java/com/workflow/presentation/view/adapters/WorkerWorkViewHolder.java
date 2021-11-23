package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import com.google.android.material.card.MaterialCardView;
import com.workflow.R;
import com.workflow.data.model.WorkerWorkModel;
import com.workflow.presentation.view.activities.WorkerDetailActivity;
import com.workflow.utils.WorkflowUtils;

import butterknife.BindString;
import butterknife.BindView;

public class WorkerWorkViewHolder extends BaseViewHolder<WorkerWorkModel
        , OnRecyclerObjectClickListener<WorkerWorkModel>> {

    @BindView(R.id.tv_item_worker_work_spk_no) TextView tvSpkNo;
    @BindView(R.id.tv_item_worker_work_article_no) TextView tvArticleNo;
    @BindView(R.id.tv_item_worker_work_quantity) TextView tvQty;
    @BindView(R.id.tv_item_worker_work_work_type) TextView tvJobType;
    @BindView(R.id.cv_item_worker_work) MaterialCardView cardView;
    @BindView(R.id.tv_item_worker_work_date_done) AppCompatTextView tvDateDone;
    @BindView(R.id.tv_item_worker_work_date_created) AppCompatTextView tvDateCreated;

    @BindString(R.string.work_list_spk_no) String strSpkNo;
    @BindString(R.string.product_list_article_no) String strArticleNo;
    @BindString(R.string.work_list_quantity) String strQty;
    @BindString(R.string.work_list_work_type) String strWorkType;
    @BindString(R.string.work_list_date_done) String strDateDone;
    @BindString(R.string.work_list_date_created) String strDateCreated;

    public WorkerWorkViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void onBind(WorkerWorkModel item, OnRecyclerObjectClickListener<WorkerWorkModel> listener) {
        tvSpkNo.setText(String.format(strSpkNo, item.getSpkNo()));
        tvDateCreated.setText(String.format(strDateCreated, WorkflowUtils.convertApiToIndonesianDate(item.getCreatedAt())));
        tvDateDone.setText(String.format(strDateDone, WorkflowUtils.convertApiToIndonesianDateTime(item.getDoneAt())));
        tvArticleNo.setText(String.format(strArticleNo, item.getArticleNo()));
        tvQty.setText(String.format(strQty, item.getQty()));
        tvJobType.setText(String.format(strWorkType
                , WorkflowUtils.getRenderedPosition(tvJobType.getContext(), item.getPosition())));

        final Context context = cardView.getContext();
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((WorkerDetailActivity) context).isActionMode()) {
                    listener.onItemSelected(getAdapterPosition(), item);
                } else {
                    listener.onItemClicked(getAdapterPosition(), item);
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
    }
}
