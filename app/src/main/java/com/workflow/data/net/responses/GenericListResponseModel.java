package com.workflow.data.net.responses;

import com.google.gson.annotations.SerializedName;
import com.workflow.data.model.MetaModel;

import java.util.List;

public class GenericListResponseModel<T> {
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private List<T> items;
    @SerializedName("message")
    private String message;
    @SerializedName("meta")
    private MetaModel metaModel;

    public GenericListResponseModel(List<T> items, MetaModel metaModel) {
        this.items = items;
        this.metaModel = metaModel;
    }

    public List<T> getItems() {
        return items;
    }

    public MetaModel getMetaModel() {
        return metaModel;
    }
}
