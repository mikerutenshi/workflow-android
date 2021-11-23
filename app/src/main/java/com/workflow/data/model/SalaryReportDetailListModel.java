package com.workflow.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SalaryReportDetailListModel {

    @SerializedName("total_cost")
    private Integer totalCost;
    @SerializedName("total_quantity")
    private Integer totalQuantity;
    @SerializedName("list")
    private List<Item> list;

    public Integer getTotalCost() {
        return totalCost;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public List<Item> getList() {
        return list;
    }

    public static class Item {
        @SerializedName("id")
        private Integer id;
        @SerializedName("spk_no")
        private Integer spkNo;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("article_no")
        private String articleNo;
        @SerializedName("product_category_name")
        private String productCategoryName;
        @SerializedName("position")
        private String position;
        @SerializedName("quantity")
        private Integer quantity;
        @SerializedName("done_at")
        private String doneAt;
        @SerializedName("unit_cost")
        private Integer unitCost;
        @SerializedName("sub_total_cost")
        private Integer subTotalCost;

        public Integer getId() {
            return id;
        }

        public Integer getSpkNo() {
            return spkNo;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getArticleNo() {
            return articleNo;
        }

        public String getProductCategoryName() {
            return productCategoryName;
        }

        public String getPosition() {
            return position;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public String getDoneAt() {
            return doneAt;
        }

        public Integer getUnitCost() {
            return unitCost;
        }

        public Integer getSubTotalCost() {
            return subTotalCost;
        }
    }
}
