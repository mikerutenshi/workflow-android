package com.workflow.presentation.presenter;

import com.workflow.data.model.DateFilterModel;

/**
 * Created by Michael on 10/07/19.
 */

public interface WorkerWorkReportPresenter extends BasePresenter {
    void getWorkerWorkReport(DateFilterModel dateFilterModel);
}
