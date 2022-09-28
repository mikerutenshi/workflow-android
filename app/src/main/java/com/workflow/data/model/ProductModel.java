package com.workflow.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Michael on 11/06/19.
 */

public class ProductModel implements MultiChoiceable, Parcelable {
    @SerializedName("product_id")
    Integer id;
    @SerializedName("product_category_id")
    Integer productCategoryId;
    @SerializedName("product_category_name")
    String productCategoryName;
    @SerializedName("article_no")
    String articleNo;
    @SerializedName("drawing_cost")
    Integer drawingCost;
    @SerializedName("sewing_cost")
    Integer sewingCost;
    @SerializedName("assembling_cost")
    Integer assemblingCost;
    @SerializedName("sole_stitching_cost")
    Integer soleStitchingCost;
    @SerializedName("insole_stitching_cost")
    Integer insoleStitchingCost;
    @SerializedName("lining_drawing_cost")
    Integer liningDrawingCost;

    private transient boolean isSelected;

    public ProductModel() {
    }

    public ProductModel(String articleNo, Integer drawingCost, Integer sewingCost, Integer assemblingCost, Integer soleStitchingCost, Integer insoleStitchingCost, Integer liningDrawingCost, Integer productCategoryId) {
        this.articleNo = articleNo;
        this.drawingCost = drawingCost;
        this.sewingCost = sewingCost;
        this.assemblingCost = assemblingCost;
        this.soleStitchingCost = soleStitchingCost;
        this.insoleStitchingCost = insoleStitchingCost;
        this.liningDrawingCost = liningDrawingCost;
        this.productCategoryId = productCategoryId;
    }

    public ProductModel(Integer id, String articleNo, Integer drawingCost, Integer sewingCost, Integer assemblingCost, Integer soleStitchingCost, Integer insoleStitchingCost, Integer liningDrawingCost, Integer productCategoryId) {
        this.id = id;
        this.articleNo = articleNo;
        this.drawingCost = drawingCost;
        this.sewingCost = sewingCost;
        this.assemblingCost = assemblingCost;
        this.soleStitchingCost = soleStitchingCost;
        this.insoleStitchingCost = insoleStitchingCost;
        this.liningDrawingCost = liningDrawingCost;
        this.productCategoryId = productCategoryId;
    }

    @Override
    public void toggleIsSelected() {
        isSelected = !isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Integer getId() {
        return id;
    }

    public String getArticleNo() {
        return articleNo;
    }

    public Integer getDrawingCost() {
        return drawingCost;
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

    public Integer getLiningDrawingCost() {
        return liningDrawingCost;
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.productCategoryId);
        dest.writeString(this.productCategoryName);
        dest.writeString(this.articleNo);
        dest.writeValue(this.drawingCost);
        dest.writeValue(this.sewingCost);
        dest.writeValue(this.assemblingCost);
        dest.writeValue(this.soleStitchingCost);
        dest.writeValue(this.insoleStitchingCost);
        dest.writeValue(this.liningDrawingCost);
    }

    protected ProductModel(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.productCategoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.productCategoryName = in.readString();
        this.articleNo = in.readString();
        this.drawingCost = (Integer) in.readValue(Integer.class.getClassLoader());
        this.sewingCost = (Integer) in.readValue(Integer.class.getClassLoader());
        this.assemblingCost = (Integer) in.readValue(Integer.class.getClassLoader());
        this.soleStitchingCost = (Integer) in.readValue(Integer.class.getClassLoader());
        this.insoleStitchingCost = (Integer) in.readValue(Integer.class.getClassLoader());
        this.liningDrawingCost = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel source) {
            return new ProductModel(source);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };
}
