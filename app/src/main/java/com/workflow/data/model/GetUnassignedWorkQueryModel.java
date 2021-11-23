package com.workflow.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GetUnassignedWorkQueryModel extends WorkQueryModel implements Parcelable {
    private String startDate;
    private String endDate;
    private String sortBy;
    private String sortDirection;

    public GetUnassignedWorkQueryModel(String search, Integer limit, Integer page, Integer workerId, String workerPos) {
        super(search, limit, page, workerId, workerPos);
    }

    public GetUnassignedWorkQueryModel(String search, Integer limit, Integer page, Integer workerId, String workerPos, String startDate, String endDate, String sortBy, String sortDirection) {
        super(search, limit, page, workerId, workerPos);
        this.startDate = startDate;
        this.endDate = endDate;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
        dest.writeString(this.sortBy);
        dest.writeString(this.sortDirection);
    }

    public GetUnassignedWorkQueryModel() {
    }

    protected GetUnassignedWorkQueryModel(Parcel in) {
        this.startDate = in.readString();
        this.endDate = in.readString();
        this.sortBy = in.readString();
        this.sortDirection = in.readString();
    }

    public static final Parcelable.Creator<GetUnassignedWorkQueryModel> CREATOR = new Parcelable.Creator<GetUnassignedWorkQueryModel>() {
        @Override
        public GetUnassignedWorkQueryModel createFromParcel(Parcel source) {
            return new GetUnassignedWorkQueryModel(source);
        }

        @Override
        public GetUnassignedWorkQueryModel[] newArray(int size) {
            return new GetUnassignedWorkQueryModel[size];
        }
    };
}
