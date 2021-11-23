package com.workflow.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SalaryReportListModel {

    @SerializedName("quantities")
    private List<Quantity> quantitiesModels;
    @SerializedName("total_cost")
    private Integer totalCost;
    @SerializedName("list")
    private List<Item> list;

    public List<Quantity> getQuantitiesModels() {
        return quantitiesModels;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public List<Item> getList() {
        return list;
    }

    public static class Quantity {
        @SerializedName("position")
        private String position;
        @SerializedName("quantity")
        private Integer quantity;

        public String getPosition() {
            return position;
        }

        public Integer getQuantity() {
            return quantity;
        }
    }

    public static class Item {
        @SerializedName("worker_id")
        private Integer workerId;
        @SerializedName("name")
        private String name;
        @SerializedName("position")
        private List<String> positions;
        @SerializedName("worker_total_cost")
        private Integer totalCost;

        @SerializedName("worker_total_quantity")
        private Integer totalQuantity;

        public Integer getWorkerId() {
            return workerId;
        }

        public String getName() {
            return name;
        }

        public Integer getTotalCost() {
            return totalCost;
        }

        public Integer getTotalQuantity() {
            return totalQuantity;
        }

        public List<String> getPositions() {
            return positions;
        }
    }
}
