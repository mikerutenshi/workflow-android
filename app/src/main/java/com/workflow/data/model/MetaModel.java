package com.workflow.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Michael on 19/07/19.
 */

public class MetaModel {
    @SerializedName("total_page")
    Integer totalPage;

    public MetaModel(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }
}
