package com.workflow.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by Michael on 09/07/19.
 */

public class WorkerWorkReportModel {
    @SerializedName("list")
    List<ReportListModel> reportListModels;
    @SerializedName("sum")
    ReportSumModel sumModel;

    public List<ReportListModel> getReportListModels() {
        return reportListModels;
    }

    public ReportSumModel getSumModel() {
        return sumModel;
    }
}
