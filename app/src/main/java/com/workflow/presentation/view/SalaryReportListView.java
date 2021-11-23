package com.workflow.presentation.view;

import com.workflow.data.model.SalaryReportListModel;
import com.workflow.data.net.responses.GenericResponseModel;
import com.workflow.presentation.view.adapters.SalaryReportListAdapter;

import java.util.HashMap;

public interface SalaryReportListView extends BaseView {
    SalaryReportListAdapter getAdapter();
    HashMap<String, String> getOptions();
    void renderData(GenericResponseModel<SalaryReportListModel> data);
    void showDataEmpty();
}
