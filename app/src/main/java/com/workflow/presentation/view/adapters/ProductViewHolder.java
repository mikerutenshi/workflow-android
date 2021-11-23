package com.workflow.presentation.view.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Group;

import com.google.android.material.card.MaterialCardView;
import com.workflow.R;
import com.workflow.data.model.ProductModel;
import com.workflow.presentation.view.activities.MainActivity;
import com.workflow.utils.WorkflowUtils;

import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by Michael on 11/06/19.
 */

class ProductViewHolder extends BaseViewHolder<ProductModel, OnRecyclerObjectClickListener<ProductModel>> {

    @BindView(R.id.tv_item_product_article_no) TextView tvArticleNo;
    @BindView(R.id.tv_item_product_category) TextView tvCategory;
    @BindView(R.id.tv_item_product_drawing_cost) TextView tvDrawingCost;
    @BindView(R.id.tv_item_product_lining_drawing_cost) TextView tvLiningDrawingCost;
    @BindView(R.id.tv_item_product_sewing_cost) TextView tvSewingCost;
    @BindView(R.id.tv_item_product_assembling_cost) TextView tvAssemblingCost;
    @BindView(R.id.tv_item_product_insole_stitching_cost) TextView tvInsoleStitchingCost;
    @BindView(R.id.tv_item_product_outsole_stitching_cost) TextView tvSoleStitchingCost;
    @BindView(R.id.layout_card_item_product) MaterialCardView cardView;
    @BindView(R.id.gr_item_product_outsole_stitching) Group grOutsoleStitching;
    @BindView(R.id.gr_item_product_insole_stitching) Group grInsoleStitching;

    @BindString(R.string.product_list_article_with_cat) String strArticleWithCat;

    public ProductViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void onBind(final ProductModel item, final OnRecyclerObjectClickListener<ProductModel> listener) {
        tvArticleNo.setText(item.getArticleNo());
        tvCategory.setText(item.getProductCategoryName());
        tvDrawingCost.setText(WorkflowUtils.convertRupiah(item.getDrawingCost()));
        tvLiningDrawingCost.setText(WorkflowUtils.convertRupiah(item.getLiningDrawingCost()));
        tvSewingCost.setText(WorkflowUtils.convertRupiah(item.getSewingCost()));
        tvAssemblingCost.setText(WorkflowUtils.convertRupiah(item.getAssemblingCost()));

        final Context context = cardView.getContext();
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((MainActivity) context).isActionMode()) {
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

        //show insole stitching cost if available
        if (item.getInsoleStitchingCost() != 0) {
            tvInsoleStitchingCost.setText(WorkflowUtils.convertRupiah(item.getInsoleStitchingCost()));
            grInsoleStitching.setVisibility(View.VISIBLE);
        } else {
            grInsoleStitching.setVisibility(View.GONE);
        }

        //show sole stitching cost if available
        if (item.getSoleStitchingCost() != null && item.getSoleStitchingCost() != 0) {
            tvSoleStitchingCost.setText(WorkflowUtils.convertRupiah(item.getSoleStitchingCost()));
            grOutsoleStitching.setVisibility(View.VISIBLE);
        } else {
            grOutsoleStitching.setVisibility(View.GONE);
        }

        //dynamic card bg color
        if (item.isSelected()) {
            cardView.setChecked(true);
        } else {
            cardView.setChecked(false);
        }
    }
}
