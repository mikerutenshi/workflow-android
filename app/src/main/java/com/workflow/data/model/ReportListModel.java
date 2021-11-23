package com.workflow.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Michael on 09/07/19.
 */

public class ReportListModel implements Parcelable {
    @SerializedName("worker_id")
    Integer workerId;
    @SerializedName("name")
    String name;
    @SerializedName("worker_total_cost")
    Integer totalCost;
    @SerializedName("worker_total_quantity")
    Integer totalQty;
    @SerializedName("position")
    String position;

    public ReportListModel(Integer workerId, String name, Integer totalCost, Integer totalQty) {
        this.workerId = workerId;
        this.name = name;
        this.totalCost = totalCost;
        this.totalQty = totalQty;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public String getName() {
        return name;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public String getPosition() {
        return position;
    }

    public ReportListModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.workerId);
        dest.writeString(this.name);
        dest.writeValue(this.totalCost);
        dest.writeValue(this.totalQty);
        dest.writeString(this.position);
    }

    protected ReportListModel(Parcel in) {
        this.workerId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.totalCost = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalQty = (Integer) in.readValue(Integer.class.getClassLoader());
        this.position = in.readString();
    }

    public static final Creator<ReportListModel> CREATOR = new Creator<ReportListModel>() {
        @Override
        public ReportListModel createFromParcel(Parcel source) {
            return new ReportListModel(source);
        }

        @Override
        public ReportListModel[] newArray(int size) {
            return new ReportListModel[size];
        }
    };
}
