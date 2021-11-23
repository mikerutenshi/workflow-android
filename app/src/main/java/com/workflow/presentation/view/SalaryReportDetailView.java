package com.workflow.presentation.view;

import com.workflow.data.model.SalaryReportDetailListModel;
import com.workflow.data.model.WorkerModel;
import com.workflow.data.net.responses.GenericResponseModel;
import com.workflow.presentation.view.adapters.SalaryReportDetailListAdapter;

import java.util.HashMap;

public interface SalaryReportDetailView extends BaseView {
    SalaryReportDetailListAdapter getAdapter();
    HashMap<String, String> getOptions();
    WorkerModel getWorkerModel();
    void renderData(GenericResponseModel<SalaryReportDetailListModel> data);
    void showDataEmpty();
}
