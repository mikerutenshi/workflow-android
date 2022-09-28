package com.workflow.data.model;

import android.widget.Checkable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWorkListModel implements Checkable {
    @SerializedName("work_id")
    Integer id;
    @SerializedName("spk_no")
    Integer spkNo;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("product_quantity")
    Integer quantity;
    @SerializedName("product_id")
    Integer productId;
    @SerializedName("article_no")
    String articleNo;
    @SerializedName("product_category_id")
    Integer productCategoryId;
    @SerializedName("product_category_name")
    String productCategoryName;
    @SerializedName("drawing_cost")
    Integer drawingCost;
    @SerializedName("lining_drawing_cost")
    Integer liningDrawingCost;
    @SerializedName("sewing_cost")
    Integer sewingCost;
    @SerializedName("assembling_cost")
    Integer assemblingCost;
    @SerializedName("sole_stitching_cost")
    Integer soleStitchingCost;
    @SerializedName("insole_stitching_cost")
    Integer insoleStitchingCost;
    @SerializedName("work_in_progress")
    List<WorkInProgress> wip;
    @SerializedName("work_done")
    List<WorkDone> workDone;
    @SerializedName("notes")
    String notes;
    private transient boolean isChecked;

    public Integer getId() {
        return id;
    }

    public Integer getSpkNo() {
        return spkNo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getArticleNo() {
        return articleNo;
    }

    public Integer getDrawingCost() {
        return drawingCost;
    }

    public Integer getLiningDrawingCost() {
        return liningDrawingCost;
    }

    public Integer getSewingCost() {
        return sewingCost;
    }

    public Integer getAssemblingCost() {
        return assemblingCost;
    }

    public Integer getSoleStitchingCost() {
        return soleStitchingCost;
    }

    public Integer getInsoleStitchingCost() {
        return insoleStitchingCost;
    }

    public List<WorkInProgress> getWip() {
        return wip;
    }

    public List<WorkDone> getWorkDone() {
        return workDone;
    }

    public String getNotes() {
        return notes;
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    @Override
    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        this.isChecked = !this.isChecked;
    }

    public static class WorkInProgress {
        @SerializedName("position")
        String position;

        public String getPosition() {
            return position;
        }
    }

    public static class WorkDone {
        @SerializedName("position")
        String position;
        @SerializedName("sum")
        Integer sum;

        public String getPosition() {
            return position;
        }

        public Integer getSum() {
            return sum;
        }
    }
}
