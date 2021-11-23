package com.workflow.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GetWorkerQueryModel extends SearchPaginatedQueryModel implements Parcelable {

    private List<String> positions;
    private String sortBy;
    private String sortDirection;

    public GetWorkerQueryModel(String search, Integer limit, Integer page, List<String> positions, String sortBy, String sortDirection) {
        super(search, limit, page);
        this.positions = positions;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }

    public GetWorkerQueryModel(List<String> positions, String sortBy, String sortDirection) {
        this.positions = positions;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }

    public List<String> getPositions() {
        return positions;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.positions);
        dest.writeString(this.sortBy);
        dest.writeString(this.sortDirection);
    }

    protected GetWorkerQueryModel(Parcel in) {
        this.positions = in.createStringArrayList();
        this.sortBy = in.readString();
        this.sortDirection = in.readString();
    }

    public static final Parcelable.Creator<GetWorkerQueryModel> CREATOR = new Parcelable.Creator<GetWorkerQueryModel>() {
        @Override
        public GetWorkerQueryModel createFromParcel(Parcel source) {
            return new GetWorkerQueryModel(source);
        }

        @Override
        public GetWorkerQueryModel[] newArray(int size) {
            return new GetWorkerQueryModel[size];
        }
    };
}
