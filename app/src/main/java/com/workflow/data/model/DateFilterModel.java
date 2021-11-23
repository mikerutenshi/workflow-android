package com.workflow.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DateFilterModel implements Parcelable {
    String startDate;
    String endDate;

    public DateFilterModel(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static final Creator<DateFilterModel> CREATOR = new Creator<DateFilterModel>() {
        @Override
        public DateFilterModel createFromParcel(Parcel in) {
            return new DateFilterModel(in);
        }

        @Override
        public DateFilterModel[] newArray(int size) {
            return new DateFilterModel[size];
        }
    };

    public String getEndDate() {
        return endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
    }

    protected DateFilterModel(Parcel in) {
        this.startDate = in.readString();
        this.endDate = in.readString();
    }

}
