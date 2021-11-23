package com.workflow.presentation.presenter;

import com.workflow.data.model.ReportDetailDateFilterModel;

/**
 * Created by Michael on 25/07/19.
 */

public interface ReportWorkerDetailPresenter extends BasePresenter {
    void getWorkDetailReport(ReportDetailDateFilterModel dateFilterModel);
}
