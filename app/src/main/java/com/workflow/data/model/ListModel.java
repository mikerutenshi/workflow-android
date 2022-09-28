package com.workflow.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Michael on 03/07/19.
 */

public class ListModel implements Parcelable {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    boolean isHighlighted;

    public ListModel() {
    }

    public ListModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    protected ListModel(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<ListModel> CREATOR = new Parcelable.Creator<ListModel>() {
        @Override
        public ListModel createFromParcel(Parcel source) {
            return new ListModel(source);
        }

        @Override
        public ListModel[] newArray(int size) {
            return new ListModel[size];
        }
    };
}
