package com.workflow.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Michael on 27/06/19.
 */

public class WorkerModel implements Parcelable, MultiChoiceable {
    @SerializedName("worker_id")
    Integer id;
    @SerializedName("name")
    String name;
    @SerializedName("position")
    List<String> position = new ArrayList<>();
    transient boolean isSelected;

    public WorkerModel() {
    }

    public WorkerModel(String name, List<String> position) {
        this.name = name;
        this.position.addAll(position);
    }

    public WorkerModel(Integer id, String name, List<String> position) {
        this.id = id;
        this.name = name;
        this.position.addAll(position);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getPositions() {
        return position;
    }

    public void setPosition(List<String> position) {
        this.position = position;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public void toggleIsSelected() {
        isSelected = !isSelected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeStringList(this.position);
    }

    protected WorkerModel(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.position.addAll(in.createStringArrayList());
    }

    public static final Creator<WorkerModel> CREATOR = new Creator<WorkerModel>() {
        @Override
        public WorkerModel createFromParcel(Parcel source) {
            return new WorkerModel(source);
        }

        @Override
        public WorkerModel[] newArray(int size) {
            return new WorkerModel[size];
        }
    };
}
