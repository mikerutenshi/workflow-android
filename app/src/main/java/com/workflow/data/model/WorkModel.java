package com.workflow.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Michael on 27/06/19.
 */

public class WorkModel implements MultiChoiceable, Parcelable {
    @SerializedName("work_id")
    Integer id;
    @SerializedName("spk_no")
    Integer spkNo;
    @SerializedName("product_id")
    Integer productId;
    @SerializedName("article_no")
    String articleNo;
    @SerializedName("product_category_id")
    String productCategoryId;
    @SerializedName("product_category_name")
    String productCategoryName;
    @SerializedName("product_quantity")
    Integer qty;
    @SerializedName("is_drawn")
    boolean isDrawn;
    @SerializedName("is_sewn")
    boolean isSewn;
    @SerializedName("is_assembled")
    boolean isAssembled;
    @SerializedName("is_sole_stitched")
    boolean isSoleStitched;
    @SerializedName("is_lining_drawn")
    boolean isLiningDrawn;
    @SerializedName("is_insole_stitched")
    boolean isInsoleStitched;
    @SerializedName("drawing_cost")
    int drawingCost;
    @SerializedName("sewing_cost")
    int sewingCost;
    @SerializedName("assembling_cost")
    int assemblingCost;
    @SerializedName("sole_stitching_cost")
    int soleStitchingCost;
    @SerializedName("lining_drawing_cost")
    int liningDrawingCost;
    @SerializedName("insole_stitching_cost")
    int insoleStitchingCost;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("updated_at")
    String updatedAt;
    @SerializedName("notes")
    String notes;

    private transient boolean isChecked = false;
    private transient boolean isSelected;

    public WorkModel() {
    }

    public WorkModel(int spkNo, Integer productId, Integer qty, String notes) {
        this.spkNo = spkNo;
        this.productId = productId;
        this.qty = qty;
        this.notes = notes;
    }

    public WorkModel(Integer id, Integer spkNo, Integer productId, Integer qty, String notes) {
        this.id = id;
        this.spkNo = spkNo;
        this.productId = productId;
        this.qty = qty;
        this.notes = notes;
    }

    public WorkModel(Integer id, Integer spkNo, String articleNo, String category, Integer qty, String createdAt, Integer productId, String notes) {
        this.id = id;
        this.spkNo = spkNo;
        this.articleNo = articleNo;
        this.productCategoryId = category;
        this.qty = qty;
        this.createdAt = createdAt;
        this.productId = productId;
        this.notes = notes;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSpkNo() {
        return spkNo;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getArticleNo() {
        return articleNo;
    }

    public Integer getQty() {
        return qty;
    }

    public String getProductCategoryId() {
        return productCategoryId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    @Override
    public void toggleIsSelected() {
        isSelected = !isSelected;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    public boolean isDrawn() {
        return isDrawn;
    }

    public boolean isSewn() {
        return isSewn;
    }

    public boolean isAssembled() {
        return isAssembled;
    }

    public boolean isSoleStitched() {
        return isSoleStitched;
    }

    public int getDrawingCost() {
        return drawingCost;
    }

    public int getSewingCost() {
        return sewingCost;
    }

    public int getAssemblingCost() {
        return assemblingCost;
    }

    public int getSoleStitchingCost() {
        return soleStitchingCost;
    }

    public boolean isLiningDrawn() {
        return isLiningDrawn;
    }

    public boolean isInsoleStitched() {
        return isInsoleStitched;
    }

    public int getLiningDrawingCost() {
        return liningDrawingCost;
    }

    public int getInsoleStitchingCost() {
        return insoleStitchingCost;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.spkNo);
        dest.writeValue(this.productId);
        dest.writeString(this.articleNo);
        dest.writeString(this.productCategoryId);
        dest.writeValue(this.qty);
        dest.writeByte(this.isDrawn ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSewn ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isAssembled ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSoleStitched ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isLiningDrawn ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isInsoleStitched ? (byte) 1 : (byte) 0);
        dest.writeInt(this.drawingCost);
        dest.writeInt(this.sewingCost);
        dest.writeInt(this.assemblingCost);
        dest.writeInt(this.soleStitchingCost);
        dest.writeInt(this.liningDrawingCost);
        dest.writeInt(this.insoleStitchingCost);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.notes);
    }

    protected WorkModel(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.spkNo = (Integer) in.readValue(Integer.class.getClassLoader());
        this.productId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.articleNo = in.readString();
        this.productCategoryId = in.readString();
        this.qty = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isDrawn = in.readByte() != 0;
        this.isSewn = in.readByte() != 0;
        this.isAssembled = in.readByte() != 0;
        this.isSoleStitched = in.readByte() != 0;
        this.isLiningDrawn = in.readByte() != 0;
        this.isInsoleStitched = in.readByte() != 0;
        this.drawingCost = in.readInt();
        this.sewingCost = in.readInt();
        this.assemblingCost = in.readInt();
        this.soleStitchingCost = in.readInt();
        this.liningDrawingCost = in.readInt();
        this.insoleStitchingCost = in.readInt();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.notes = in.readString();
    }

    public static final Creator<WorkModel> CREATOR = new Creator<WorkModel>() {
        @Override
        public WorkModel createFromParcel(Parcel source) {
            return new WorkModel(source);
        }

        @Override
        public WorkModel[] newArray(int size) {
            return new WorkModel[size];
        }
    };
}
