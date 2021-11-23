package com.workflow.data.model;

import android.os.Parcel;

public class ReportDetailDateFilterModel extends DateFilterModel {
    Integer workerId;
    String position;

    public ReportDetailDateFilterModel(String startDate, String endDate, Integer workerId, String position) {
        super(startDate, endDate);
        this.workerId = workerId;
        this.position = position;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(this.workerId);
        dest.writeString(this.position);
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
    }

    protected ReportDetailDateFilterModel(Parcel in) {
        super(in);
        this.workerId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.position = in.readString();
        this.startDate = in.readString();
        this.endDate = in.readString();
    }

    public static final Creator<ReportDetailDateFilterModel> CREATOR = new Creator<ReportDetailDateFilterModel>() {
        @Override
        public ReportDetailDateFilterModel createFromParcel(Parcel source) {
            return new ReportDetailDateFilterModel(source);
        }

        @Override
        public ReportDetailDateFilterModel[] newArray(int size) {
            return new ReportDetailDateFilterModel[size];
        }
    };
}
